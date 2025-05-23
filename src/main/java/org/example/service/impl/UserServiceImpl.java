package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.api.responses.Errors;
import org.example.dao.dto.request.RequestUserDTO;
import org.example.dao.dto.request.SignUpDTO;
import org.example.dao.dto.response.ResponseUserDTO;
import org.example.dao.entity.Account;
import org.example.dao.entity.EmailData;
import org.example.dao.entity.PhoneData;
import org.example.dao.entity.User;
import org.example.dao.mapper.UserMapper;
import org.example.dao.repository.AccountRepository;
import org.example.dao.repository.EmailDataRepository;
import org.example.dao.repository.PhoneDataRepository;
import org.example.dao.repository.UserRepository;
import org.example.dao.repository.filter.FilterUserParam;
import org.example.dao.repository.specification.UserSpecification;
import org.example.service.UserService;
import org.example.service.exception.extend.email.EmailTakenException;
import org.example.service.exception.extend.phone.PhoneTakenException;
import org.example.service.exception.extend.user.UserNotFoundException;
import org.example.service.sequrity.service.impl.UserContext;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final EmailDataRepository emailDataRepo;
    private final PhoneDataRepository phoneDataRepo;
    private final AccountRepository accountRepo;
    private final UserMapper userMapper;
    private final UserContext userContext;
    private final PasswordEncoder passwordEncoder;


    @Cacheable(value = "users::filtered", key = "#filter.toString().concat('-').concat(#pageable.pageNumber.toString())")
    @Transactional(readOnly = true)
    public Page<ResponseUserDTO> getUsersWithFilterAndPagination(FilterUserParam filter, Pageable pageable) {
        Specification<User> spec = Specification
                .where(UserSpecification.nameLike(filter.getName()))
                .and(UserSpecification.emailEquals(filter.getEmail()))
                .and(UserSpecification.phoneEquals(filter.getPhone()))
                .and(UserSpecification.dateOfBirthAfter(filter.getDateOfBirth()));

        return userRepo.findAll(spec, pageable).map(userMapper::toResponseUserDTO);
    }

    @Caching(evict = {
            @CacheEvict(value = "users::email", allEntries = true),
            @CacheEvict(value = "users::id", key = "#userContext.getUserDTO().id"),
            @CacheEvict(value = "users::filtered", allEntries = true)
    })
    @Transactional
    public ResponseUserDTO updateUser(RequestUserDTO requestUserDTO) {
        User user = userRepo.findById(userContext.getUserDTO().getId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.getEmailDataSet().clear();
        emailDataRepo.deleteAllByUserId(user.getId());

        requestUserDTO.getEmail().forEach(email -> {
            if (userRepo.existsByEmailAndIdNot(email, user.getId())) {
                throw new EmailTakenException("Email " + email + " is already in use", Errors.EMAIL_TAKEN);
            }
            EmailData emailData = new EmailData();
            emailData.setEmail(email);
            emailData.setUser(user);
            emailDataRepo.save(emailData);
            user.getEmailDataSet().add(emailData);
        });

        user.getPhoneDataSet().clear();
        phoneDataRepo.deleteAllByUserId(user.getId());

        requestUserDTO.getPhone().forEach(phone -> {
            if (userRepo.existsByPhoneAndIdNot(phone, user.getId())) {
                throw new PhoneTakenException("Phone " + phone + " is already in use", Errors.PHONE_TAKEN);
            }
            PhoneData phoneData = new PhoneData();
            phoneData.setPhone(phone);
            phoneData.setUser(user);
            phoneDataRepo.save(phoneData);
            user.getPhoneDataSet().add(phoneData);
        });

        userRepo.save(user);
        return userMapper.toResponseUserDTO(user);
    }

    @Caching(evict = {
            @CacheEvict(value = "users::email", allEntries = true),
            @CacheEvict(value = "users::filtered", allEntries = true)
    })
    @Transactional
    public ResponseUserDTO createUser(SignUpDTO signUpDTO) {

        User user = new User();

        signUpDTO.email().forEach(email -> {
            if (userRepo.existsByEmail(email))
                throw new EmailTakenException("Email " + email + " is already in use", Errors.EMAIL_TAKEN);
            EmailData emailData = new EmailData();
            emailData.setEmail(email);
            user.getEmailDataSet().add(emailData);
            emailDataRepo.save(emailData);
        });
        signUpDTO.phone().forEach(phone -> {
            if (userRepo.existsByPhone(phone))
                throw new PhoneTakenException("Phone " + phone + " is already in use", Errors.PHONE_TAKEN);
            PhoneData phoneData = new PhoneData();
            phoneData.setPhone(phone);
            user.getPhoneDataSet().add(phoneData);
            phoneDataRepo.save(phoneData);
        });

        user.setPassword(passwordEncoder.encode(signUpDTO.password()));


        user.setUsername(signUpDTO.username());
        user.setBirthDate(signUpDTO.birthday());

        Account account = new Account();
        account.setUser(user);
        account.setBalance(signUpDTO.balance());

        accountRepo.save(account);

        userRepo.save(user);

        return userMapper.toResponseUserDTO(user);
    }
}
