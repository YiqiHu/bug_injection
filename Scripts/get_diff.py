import urllib.request
import requests

commit_file = open("../Data/Projects/Hadoop-HDFS/Output/github_all_commits.txt", "r")
lines = commit_file.read().split('\n')
print(lines)
print(len(lines))

for commit in lines:
    URL = "https://github.com/apache/hadoop/commit/" + commit + ".diff"
    response = requests.get(URL)
    open("../Data/Projects/Hadoop-HDFS/Output/Diff/" + commit + ".diff", "wb").write(response.content)

#     URL = "https://github.com/apache/hadoop/commit/" + commit
#     # response = requests.get(URL)
#     # open("../Data/Projects/Hadoop-HDFS/Output/diff/"+ commit + ".diff", "wb").write(response.content)
#
#     # response = wget.download(URL, "../Data/Projects/Hadoop-HDFS/Output/diff/"+ commit + ".diff")
#
#     urllib.request.urlretrieve("https://github.com/apache/hadoop/commit/" + commit, commit + ".diff")

# urllib.request.urlretrieve("http://maps.googleapis.com/maps/api/geocode/json?address=googleplex&sensor=false", "Example.json")

commit_file.close()