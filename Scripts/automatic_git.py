import subprocess
import os

# def movefileAndPush():
#     try:
#         x = subprocess.run(['cp','Project_Repo_Details.csv','bitbucket_repo_report/Project_Repo_Details.csv'])
#         print(x.stdout)
#         print(x.stderr)
#         os.chdir("/dirs/Repo-report_scripts/bitbucket_repo_report")
#         subprocess.run(['git','config','--global','user.email','test@gmail.com'])
#         subprocess.run(['git','add','Project_Repo_Details.csv'])
#         subprocess.run(['git','commit','-m','PQR-99 adding repo report'])
#         subprocess.run(['git','push','https://username:password@gitreporul','--all'])
#     except Exception as e:
#         print("Error occured :".format(e))
# if __name__ == '__main__':
#    movefileAndPush()

# --------------------------------------

# def open_files():
#     commit_file = open("../Data/Projects/Hadoop-HDFS/Output/github_all_commits.txt", "r")
#     lines = commit_file.read().split('\n')
#     return lines
#
# def run(*args):
#     return subprocess.check_call(['git'] + list(args))
#
# def diff():
#     # for commit in open_files():
#         subprocess.Popen("diff-tree", "--no-commit-id", "--name-only", "r", "e584e573588f8721daecd572ffb9f3148cf485ac")
#         # print(commit)
#
# if __name__ == "__main__":
#    diff()

# a = subprocess.check_output(["git", "diff-tree", "--no-commit-id", "--name-only", "-r", "e584e573588f8721daecd572ffb9f3148cf485ac"], shell=True)
# print(a)

process = subprocess.Popen(["git", "diff-tree", "--no-commit-id", "--name-only", "-r", "e584e573588f8721daecd572ffb9f3148cf485ac"], stdout=subprocess.PIPE, stderr=subprocess.PIPE)
out, err = process.communicate()
print(out.decode('ascii'))

