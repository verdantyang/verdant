def switch_dict(x):
    switcher = {
        0: "zero",
        1: "one",
        2: "two"
    }
    return switcher.get(x, "no")


def switch_lambda(type, x):
    switcher = {
        'a': lambda x: x * 5,
        'b': lambda x: x + 7,
        'c': lambda x: x - 2
    }
    return switcher.get(type, lambda x: x)(x)
    # return switcher[x]();


if __name__ == '__main__':
    print switch_dict(3)
    print switch_lambda('d', 7)
