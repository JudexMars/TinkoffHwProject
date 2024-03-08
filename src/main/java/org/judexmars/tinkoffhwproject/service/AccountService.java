package org.judexmars.tinkoffhwproject.service;

import lombok.RequiredArgsConstructor;
import org.judexmars.tinkoffhwproject.dto.account.AccountDto;
import org.judexmars.tinkoffhwproject.dto.account.CreateAccountDto;
import org.judexmars.tinkoffhwproject.exception.AccountAlreadyExistsException;
import org.judexmars.tinkoffhwproject.exception.AccountNotFoundException;
import org.judexmars.tinkoffhwproject.exception.ConfirmPasswordException;
import org.judexmars.tinkoffhwproject.exception.NoSuchRoleException;
import org.judexmars.tinkoffhwproject.mapper.AccountMapper;
import org.judexmars.tinkoffhwproject.model.AccountEntity;
import org.judexmars.tinkoffhwproject.model.RoleEntity;
import org.judexmars.tinkoffhwproject.repository.AccountRepository;
import org.judexmars.tinkoffhwproject.repository.RoleRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;

/**
 * Service that manages user accounts
 */
@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    private final AccountMapper accountMapper;

    private final RoleRepository roleRepository;

    /**
     * Load {@link UserDetails by username}
     * @param username provided username
     * @return {@link UserDetails}
     * @throws UsernameNotFoundException if there's no user with such username
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws AccountNotFoundException {
        return accountRepository.findByUsername(username).orElseThrow(() -> new AccountNotFoundException(username));
    }

    /**
     * Get account by username
     * @param username username of the selected account
     * @return account as {@link AccountDto}
     */
    public AccountDto getByUsername(String username) {
        return accountMapper.accountEntityToAccountDto(((AccountEntity) loadUserByUsername(username)));
    }

    /**
     * Create new account from provided DTO
     * @param createAccountDto dto containing all the information needed for creation
     * @return created account as {@link AccountDto}
     */
    public AccountDto createAccount(CreateAccountDto createAccountDto) {
        if (accountRepository.findByUsername(createAccountDto.username()).isPresent()) {
            throw new AccountAlreadyExistsException();
        }
        if (!createAccountDto.password().equals(createAccountDto.confirmPassword())) {
            throw new ConfirmPasswordException();
        }
        var account = accountMapper.createAccountDtoToAccount(createAccountDto);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setEnabled(true);
        var roles = new LinkedHashSet<RoleEntity>();
        roles.add(getDefaultRole());
        account.setRoles(roles);
        return accountMapper.accountEntityToAccountDto(accountRepository.save(account));
    }

    /**
     * Get role entity by its name
     * @param name name of the role
     * @return {@link RoleEntity}
     */
    public RoleEntity getRoleByName(String name) {
        return roleRepository.findByName(name).orElseThrow(() -> new NoSuchRoleException(name));
    }

    /**
     * Get default role entity
     * @return {@link RoleEntity}
     */
    public RoleEntity getDefaultRole() {
        return getRoleByName("ROLE_USER");
    }
}
