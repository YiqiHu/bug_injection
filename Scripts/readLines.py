import os
import matplotlib.pyplot as plt
import numpy as np

path = "/Users/moeachto/PycharmProjects/bug_injection/Data/TestFiles/Hadoop-HDFS/"
files = os.listdir(path)
counts_add = []
counts_delete = []
outfile = open('added.txt', 'w')


def count_add():
    n_add = 0
    for file in files:
        filepath = path + file
        if filepath.__contains__("added"):
            file = open(filepath, "r")
            n_add += 1
            num = len(file.readlines())
            counts_add.append(num)
    print("added: ", counts_add)
    print("# of added files: ", n_add)
    print("max of added LoC: ", np.max(counts_add))
    print("min of added LoC: ", np.min(counts_add))
    print("mean of added LoC: ", np.mean(counts_add))
    print("std of added LoC: ", np.std(counts_add))
    return n_add


def count_delete():
    n_delete = 0
    for file in files:
        filepath = path + file
        if filepath.__contains__("deleted"):
            file = open(filepath, "r")
            n_delete += 1
            num = len(file.readlines())
            counts_delete.append(num)

    print("deleted: ", counts_delete)
    print("# of deleted files: ", n_delete)
    print("max of deleted LoC: ", np.max(counts_delete))
    print("min of deleted LoC: ", np.min(counts_delete))
    print("mean of deleted LoC: ", np.mean(counts_delete))
    print("std of deleted LoC: ", np.std(counts_delete))
    return n_delete


def draw_image():

    x = range(1, 377)

    plt.bar(x, counts_add, label="data")

    plt.xlabel("#")
    plt.ylabel("# of added LoC")
    plt.title("Added LoC")
    plt.legend()
    plt.show()
    plt.savefig("2.png")

if __name__ == "__main__":
    count_add()
    count_delete()
    draw_image()
