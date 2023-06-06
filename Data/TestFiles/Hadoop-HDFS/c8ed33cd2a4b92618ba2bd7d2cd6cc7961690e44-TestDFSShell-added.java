
    runCmd(shell, "-chown", "MYCOMPANY+user.name:hadoop", file);
    confirmOwner("MYCOMPANY+user.name", "hadoop", fs, path);
