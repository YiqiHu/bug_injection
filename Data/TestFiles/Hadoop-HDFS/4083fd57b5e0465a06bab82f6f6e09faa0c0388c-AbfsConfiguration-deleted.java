import java.util.Map;
  private Map<String, String> storageAccountKeys;

    validateStorageAccountKeys();
  void validateStorageAccountKeys() throws InvalidConfigurationValueException {
    Base64StringConfigurationBasicValidator validator = new Base64StringConfigurationBasicValidator(
        FS_AZURE_ACCOUNT_KEY_PROPERTY_NAME, "", true);
    this.storageAccountKeys = rawConfig.getValByRegex(FS_AZURE_ACCOUNT_KEY_PROPERTY_NAME_REGX);

    for (Map.Entry<String, String> account : storageAccountKeys.entrySet()) {
      validator.validate(account.getValue());
    }
  }

