import static org.apache.hadoop.fs.s3a.commit.CommitConstants.TASK_ATTEMPT_ID;
    pendingSet.putExtraData(TASK_ATTEMPT_ID, taskId);
      // We will overwrite if there exists a pendingSet file already
      pendingSet.save(getDestFS(), taskOutcomePath, true);
