          if (Time.monotonicNow() - sumAndCount.getSnapshotTimeStamp()
              < recordValidityMs) {
            totalCount += sumAndCount.getCount();
            totalSum += sumAndCount.getSum();
          }
