import subprocess
import sys

commit_file = open("Data/Projects/Hadoop-HDFS/Output/github_all_commits.txt", "r")
lines = commit_file.read().split('\n')

def diff_filename():
    for commit in lines:
        process = subprocess.Popen(
            ["git", "diff-tree", "--no-commit-id", "--name-only", "-r", commit],
            stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        out, err = process.communicate()

        original_stdout = sys.stdout
        with open("filename-" + commit + ".txt", 'w') as f:
            sys.stdout = f  # Change the standard output to the file we created.
            print(out.decode('ascii'))
            sys.stdout = original_stdout  # Reset the standard output to its original value


def git_blame():
    for commit in lines:
        files = open("filename-" + commit + ".txt", "r")
        filenames = files.read().split('\n')

        for filename in filenames:
            process = subprocess.Popen(
                ["git", "blame", "--no-commit-id", "--name-only", "-r", commit],
                stdout=subprocess.PIPE, stderr=subprocess.PIPE)
            out, err = process.communicate()

            original_stdout = sys.stdout
            with open("filename-" + commit + ".txt", 'w') as f:
                sys.stdout = f  # Change the standard output to the file we created.
                print(out.decode('ascii'))
                sys.stdout = original_stdout  # Reset the standard output to its original value


def commit_log():
    for commit in lines:
        process = subprocess.Popen(
            ["git", "log", "-1", "--stat", commit],
            stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        out, err = process.communicate()

        original_stdout = sys.stdout
        with open("commitlog-" + commit + ".txt", 'w') as f:
            sys.stdout = f  # Change the standard output to the file we created.
            print(out.decode('utf8'))
            sys.stdout = original_stdout  # Reset the standard output to its original value

commit_log()
