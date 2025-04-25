package io.spring.uni_portal.dto.User;

public class UserProfileUpdateDTO {

    private String email;
    private String phoneNumber;
    private String address;
    private String ethnicGroup;
    private String idNumber;
    private String placeOfBirth;
    private String permanentResident;
    private String bank;
    private String bankAccountOwner;
    private String bankAccountNumber;
    private String status; // Trạng thái tài khoản, ví dụ "active", "inactive"

    // Constructors
    public UserProfileUpdateDTO(String email, String phoneNumber, String address, String ethnicGroup,
                                String idNumber, String placeOfBirth, String permanentResident, String bank,
                                String bankAccountOwner, String bankAccountNumber, String status) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.ethnicGroup = ethnicGroup;
        this.idNumber = idNumber;
        this.placeOfBirth = placeOfBirth;
        this.permanentResident = permanentResident;
        this.bank = bank;
        this.bankAccountOwner = bankAccountOwner;
        this.bankAccountNumber = bankAccountNumber;
        this.status = status;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEthnicGroup() {
        return ethnicGroup;
    }

    public void setEthnicGroup(String ethnicGroup) {
        this.ethnicGroup = ethnicGroup;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getPermanentResident() {
        return permanentResident;
    }

    public void setPermanentResident(String permanentResident) {
        this.permanentResident = permanentResident;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankAccountOwner() {
        return bankAccountOwner;
    }

    public void setBankAccountOwner(String bankAccountOwner) {
        this.bankAccountOwner = bankAccountOwner;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
