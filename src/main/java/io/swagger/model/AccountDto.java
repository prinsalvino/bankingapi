package io.swagger.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountDto {
    private Boolean active;
    private Long userId;
    private Account.CurrencyEnum currency;
    private Account.TypeEnum type;
    private Integer balance ;


}
