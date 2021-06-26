package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * Body2
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-06-02T11:15:57.209Z[GMT]")


public class Body2 {
    @JsonProperty("accountFrom")
    private String accountFrom = null;

    @JsonProperty("accountTo")
    private String accountTo = null;

    @JsonProperty("amount")
    private Double amount = null;

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("userPerformingId")
    private Long userPerformingId = null;
    @JsonProperty("transactionType")
    private TransactionTypeEnum transactionType = null;

    public Body2 accountFrom(String accountFrom) {
        this.accountFrom = accountFrom;
        return this;
    }

    /**
     * Get accountFrom
     *
     * @return accountFrom
     **/
    @Schema(example = "NL01INHO0000000000", description = "")

    public String getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(String accountFrom) {
        this.accountFrom = accountFrom;
    }

    public Body2 accountTo(String accountTo) {
        this.accountTo = accountTo;
        return this;
    }

    /**
     * Get accountTo
     *
     * @return accountTo
     **/
    @Schema(example = "NL01INHO0000000000", description = "")

    public String getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(String accountTo) {
        this.accountTo = accountTo;
    }

    public Body2 amount(Double amount) {
        this.amount = amount;
        return this;
    }

    /**
     * Get amount
     *
     * @return amount
     **/
    @Schema(example = "100", description = "")

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Body2 description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Get description
     *
     * @return description
     **/
    @Schema(example = "Money for your new RB-17", description = "")

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Body2 userPerformingId(Long userPerformingId) {
        this.userPerformingId = userPerformingId;
        return this;
    }

    /**
     * Get userPerformingId
     *
     * @return userPerformingId
     **/
    @Schema(example = "10000000001", description = "")

    public Long getUserPerformingId() {
        return userPerformingId;
    }

    public void setUserPerformingId(Long userPerformingId) {
        this.userPerformingId = userPerformingId;
    }

    public Body2 transactionType(TransactionTypeEnum transactionType) {
        this.transactionType = transactionType;
        return this;
    }

    /**
     * Get transactionType
     *
     * @return transactionType
     **/
    @Schema(example = "Deposit", description = "")

    public TransactionTypeEnum getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionTypeEnum transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Body2 body2 = (Body2) o;
        return Objects.equals(this.accountFrom, body2.accountFrom) &&
                Objects.equals(this.accountTo, body2.accountTo) &&
                Objects.equals(this.amount, body2.amount) &&
                Objects.equals(this.description, body2.description) &&
                Objects.equals(this.userPerformingId, body2.userPerformingId) &&
                Objects.equals(this.transactionType, body2.transactionType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountFrom, accountTo, amount, description, userPerformingId, transactionType);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Body2 {\n");

        sb.append("    accountFrom: ").append(toIndentedString(accountFrom)).append("\n");
        sb.append("    accountTo: ").append(toIndentedString(accountTo)).append("\n");
        sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    userPerformingId: ").append(toIndentedString(userPerformingId)).append("\n");
        sb.append("    transactionType: ").append(toIndentedString(transactionType)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

    /**
     * Gets or Sets transactionType
     */
    public enum TransactionTypeEnum {
        DEPOSIT("Deposit"),

        WITHDRAWAL("Withdrawal"),

        TRANSFER("Transfer");

        private String value;

        TransactionTypeEnum(String value) {
            this.value = value;
        }

        @JsonCreator
        public static TransactionTypeEnum fromValue(String text) {
            for (TransactionTypeEnum b : TransactionTypeEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }
    }
}
