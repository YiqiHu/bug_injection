import org.apache.hadoop.HadoopIllegalArgumentException;
      if (policy == null) {
        throw new HadoopIllegalArgumentException(
            CommonConfigurationKeys.HADOOP_SECURITY_AUTHORIZATION
                + "is configured to true but service-level"
                + "authorization security policy is null.");
      }
