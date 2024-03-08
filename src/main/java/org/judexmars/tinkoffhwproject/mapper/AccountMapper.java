package org.judexmars.tinkoffhwproject.mapper;

import org.judexmars.tinkoffhwproject.dto.account.AccountDto;
import org.judexmars.tinkoffhwproject.dto.account.CreateAccountDto;
import org.judexmars.tinkoffhwproject.model.AccountEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountDto accountEntityToAccountDto(AccountEntity accountEntity);

    AccountEntity createAccountDtoToAccount(CreateAccountDto createAccountDto);


}
