import org.apache.hadoop.fs.azurebfs.diagnostics.Base64StringConfigurationBasicValidator;

      // Validating the key.
      validateStorageAccountKey(key);
    } catch (IllegalAccessException | InvalidConfigurationValueException e) {

  /**
   * A method to validate the storage key.
   *
   * @param key the key to be validated.
   * @throws InvalidConfigurationValueException
   */
  private void validateStorageAccountKey(String key)
      throws InvalidConfigurationValueException {
    Base64StringConfigurationBasicValidator validator = new Base64StringConfigurationBasicValidator(
        ConfigurationKeys.FS_AZURE_ACCOUNT_KEY_PROPERTY_NAME, "", true);

    validator.validate(key);
  }
