
def dict_generator(indict, pre=None):
    """
    将一个未知深度字典拍平成列表
    tips:
    1. list[:-1]为dict的key, list[-1]为dict的value
    2. 未知深度字典中的list中的元素必须是字典
    :param indict: dictionary
    :param pre: dictionary's keys
    :return: content is a generator of list
    """
    pre = pre[:] if pre else []
    if isinstance(indict, dict):
        for key, value in indict.items():
            if isinstance(value, dict):
                for d in dict_generator(value, pre + [key]):
                    yield d
            elif isinstance(value, list) or isinstance(value, tuple):
                for v in value:
                    for d in dict_generator(v, pre + [key]):
                        yield d
            else:
                yield pre + [key, value]
    else:
        yield pre + [indict]


if __name__ == '__main__':
    # 深度优先
    # list里的元素是dictionary
    t = dict_generator({'root': {'root': {'root': [{'a': 1}]}, 'dd': 2}, 'leaf': 3})
    for l in t:
        print(l)
