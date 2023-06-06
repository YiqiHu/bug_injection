from pathlib import Path
import pandas as pd
from pydriller import Repository
import datetime
import io
import os

# This script uses all bugfix issues from Jira, then maps them to Github commits
# to find the Github commits associated with these bugfixes.


def log(msg):
    print("[", datetime.datetime.now(), "] ", msg, sep="")


start_time = datetime.datetime.now()

projects = [
    {
        'name': 'Hadoop-HDFS',
        'github_link': 'https://github.com/apache/hadoop',
        # replace with list of jira links
        'jira_link': '/Hadoop-HDFS/Jira/Hadoop2020-2023.csv',
        'issue_heads': ['HADOOP']
    }
]

data_folder = '../Data/Projects/'

# Get all bugfix commits between these times
dt1 = datetime.datetime(2020, 1, 1, 0, 0, 0)
dt2 = datetime.datetime(2022, 12, 31, 23, 59, 59)

for project in projects:
    num_github_commits = 0
    num_jira_bugfixes = 0
    num_github_bugfixes = 0
    github_bugfixes = []

    # Create output folder in /Projects/
    Path("../Data/Projects/" + project['name'] +
         "/Output/").mkdir(parents=True, exist_ok=True)

    # Erase log files
    open(data_folder + "/" +
         project['name'] + "/Output/github_all_commits.txt", 'w').close()
    open(data_folder + "/" +
         project['name'] + "/Output/github_bugfix_commits.txt", 'w').close()
    open(data_folder + "/" +
         project['name'] + "/Output/github_missing_commits.txt", 'w').close()

    log("---------")
    log(project['name'])

    # Get bug issues from Jira CSV download
    #log("Getting bugfix issues from Jira")
    df_bugs = pd.read_csv(data_folder + project['jira_link'])
    jira_bug_keys = df_bugs['Issue key'].tolist()

    num_jira_bugfixes = len(jira_bug_keys)

    # Print all jira bugfix issues to log file
    f = open(data_folder + "/" +
             project['name'] + "/Output/jira_bugfix_issues.txt", 'w')
    for item in jira_bug_keys:
        f.write(item)
        f.write('\n')
    f.close()

    commits_total = []
    df_commits = pd.DataFrame(columns=['commit', 'key'])

    #log("Connecting to GitHub")
    for commit in Repository(project['github_link'], since=dt1, to=dt2).traverse_commits():
        num_github_commits += 1

        # Print all github commits to log file
        f = open(data_folder + "/" +
                 project['name'] + "/Output/github_all_commits.txt", 'a')
        f.write(commit.hash)
        f.write('\n')
        f.close()

        # Get issue key from the github commit
        if '-' in commit.msg:
            key = (commit.msg).split('-')
            key_head = key[0]

            # break here if not in project['issue heads]??

            # Exclude commits without appropriate issue key at beginning
            if key_head in project['issue_heads']:
                key_num = ''
                # First set of consecutive numbers after split are the key number
                for ch in key[1]:
                    if ch.isnumeric():
                        key_num = key_num + ch
                    else:
                        break

                key = key_head + '-' + key_num
                # row = {'commit': commit, 'key': key}
                #df_commits = df_commits.append(row, ignore_index=True)

        # Match github commits with bugfix jira issues
        # This leave us with bugfix github commits
        if key in jira_bug_keys:
            #print("Bugfix:   ", commit.hash, "  ", key)
            num_github_bugfixes += 1

            # Print all bugfix github commits to log file
            f = open(data_folder + "/" +
                     project['name'] + "/Output/github_bugfix_commits.txt", 'a')
            f.write(commit.hash)
            f.write('    ')
            f.write(key)
            f.write('\n')
            f.close()

            # Also track the keys. Will be used to check with jira issues weren't found in github commits
            github_bugfixes.append(key)

            output_file_loc = data_folder + \
                '../TestFiles/' + project['name'] + '/'

            if not os.path.exists(output_file_loc):
                os.makedirs(output_file_loc)

            #log("Getting source code before/after for bugfix commit " + commit.hash)
            for file in commit.modified_files:
                #print("   ", file.filename)
                # Only use .java files
                if ".java" in file.filename:
                    # Print file before commit
                    if (file.source_code_before):
                        f_name = output_file_loc + "Before/" + commit.hash + "-" + \
                            (file.filename).replace(
                                ".java", ".txt")

                        f = io.open(f_name, "w", encoding="utf-8")
                        f.write(file.source_code_before)
                        f.close()

                    # Print file after commit
                    if (file.source_code):
                        f_name = output_file_loc + "After/" + commit.hash + "-" + \
                            (file.filename).replace(
                                ".java", ".txt")

                        f = io.open(f_name, "w", encoding="utf-8")
                        f.write(file.source_code)
                        f.close()

                    # diff_parsed contains dict with "added" and "deleted" keys, each contains
                    # tuple (int, str) of line num, line text
                    # if (file.source_code) and (file.source_code_before):
                    #     f_name = output_file_loc + commit.hash + "-" + \
                    #         (file.filename).replace(
                    #             ".java", "-added") + ".java"
                    #
                    #     f = io.open(f_name, "w", encoding="utf-8")
                    #     # print(file.diff_parsed)['added']
                    #     # print(file.diff_parsed)['added'][1]
                    #     for tup in file.diff_parsed['added']:
                    #         f.write(tup[1])
                    #         f.write('\n')
                    #
                    #     f.close()
                    #
                    #     f_name = output_file_loc + commit.hash + "-" + \
                    #         (file.filename).replace(
                    #             ".java", "-deleted") + ".java"
                    #
                    #     f = io.open(f_name, "w", encoding="utf-8")
                    #     for tup in file.diff_parsed['deleted']:
                    #         f.write(tup[1])
                    #         f.write('\n')
                    #
                    #     f.close()

    # for key in jira_bug_keys:
    #     if key not in github_bugfixes:
    #         # Print jira bugfixes that wenren't in github commits to log file
    #         f = open(data_folder + "/" +
    #                  project['name'] + "/Output/github_missing_commits.txt", 'a')
    #         f.write(key)
    #         f.write('\n')
    #         f.close()

    log("Total GitHub Commits: " + str(num_github_commits))
    log("Total Jira Bug Fixes: " + str(num_jira_bugfixes))
    log("Total GitHub Bug Fixes: " + str(num_github_bugfixes))

print("Run time: " + str(datetime.datetime.now() - start_time))
