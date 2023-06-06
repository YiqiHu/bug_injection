  /**
   * Set the last login time for logged in user
   * @param loginTime the number of milliseconds since the beginning of time
   */
  private void setLastLogin(long loginTime) {
    user.setLastLogin(loginTime);
  }

        ugi.setLastLogin(Time.now());
