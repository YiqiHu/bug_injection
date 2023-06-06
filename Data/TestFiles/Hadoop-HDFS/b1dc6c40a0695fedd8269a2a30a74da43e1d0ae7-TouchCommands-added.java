        + OPTION_USE_TIMESTAMP + " TIMESTAMP (yyyyMMdd:HHmmss) ] "
        + "[-" + OPTION_DO_NOT_CREATE_FILE + "] <path> ...";
            + " Use specified timestamp instead of current time\n"
            + " TIMESTAMP format yyyyMMdd:HHmmss\n"
              "Unable to parse the specified timestamp "+ timestamp
              + ". The expected format is " + dateFormat.toPattern(), e);
