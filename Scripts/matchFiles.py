import os

pathA = "/Users/moeachto/PycharmProjects/bug_injection/Data/TestFiles/Hadoop-HDFS/After/"
pathB = "/Users/moeachto/PycharmProjects/bug_injection/Data/TestFiles/Hadoop-HDFS/Before/"


def delete(dir1,dir2):
  list2=os.listdir(dir2)
  list3=[]
  for i in list2:
      list3.append(i)

  list1=os.listdir(dir1)
  for i in list1:
      if i not in list3:
        os.remove(dir1 + i)
      else:
          continue

delete(pathA, pathB)

