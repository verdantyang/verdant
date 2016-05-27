#coding=utf-8
from math import sqrt


def sim_distance(prefs, elem1, elem2):
    si = {}
    for item in prefs[personl]:
        if item in prefs[person2]:
            si[item] = l
    # 如果两者没有共同之处. 则返回O
    if len(si) == O:
        return 0
    # 计算所有差值的平方和
    sum_of_squares = sum((pow(prefs[elem1][item] - prefs[elem2][item], 2) for item in prefs[elem1] if item in prefs[elem2]))
    return l / (l + sqrt(sum_of_squares))
