import static org.apache.hadoop.fs.s3a.audit.S3AAuditConstants.AUDIT_ENABLED;
 * Verify that audit managers are disabled if set to false.
    conf.setBoolean(AUDIT_ENABLED, false);
   * Verify that the auditor is the no-op auditor if auditing is disabled.
