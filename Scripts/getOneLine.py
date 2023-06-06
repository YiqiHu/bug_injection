import os

path = "/Users/moeachto/PycharmProjects/bug_injection/Data/TestFiles/Hadoop-HDFS/Before/"
files = os.listdir(path)
outfile = open('buggy.txt', 'w')

for file in files:
    filepath = path + file
    with open(filepath.replace(".txt", "-oneline.txt"), "w") as f:
        with open(filepath, "r") as fp:
            for line in fp:
                if not line.startswith("import") and not line.startswith("package") and not line.startswith("@"):
                    line = str(line).replace("\n", "")
                    f.write(line)
    for line in open(filepath.replace(".txt", "-oneline.txt")):
        outfile.writelines(line)
    outfile.write('\r\n')


# for file in files:
#     filepath = path + file
#     for line in open(filepath):
#         outfile.writelines(line)
#     outfile.write('\n')

outfile.close()