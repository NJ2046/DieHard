import re
import itertools
import reprlib
from random import randint

RE_WORD = re.compile('\w+')


class Sentence:
    def __init__(self, text):
        self.text = text
        self.words = RE_WORD.findall(text)

    def __getitem__(self, index):
        return self.words[index]

    def __len__(self):
        return len(self.words)

    def __repr__(self):
        return 'Sentence(%s)' % reprlib.repr(self.text)


class SentenceIterator:
    def __init__(self, text):
        self.words = RE_WORD.findall(text)
        self.index = 0

    def __next__(self):
        try:
            word = self.words[self.index]
        except IndexError:
            raise StopIteration()
        self.index += 1
        return word

    def __iter__(self):
        return self


class PSentence:
    def __init__(self, text):
        self.text = text
        self.words = RE_WORD.findall(text)

    def __repr__(self):
        return 'Sentence(%s)' % reprlib.repr(self.text)

    def __iter__(self):
        for word in self.words:
            yield word
        return


class ISentence:

    def __init__(self, text):
        self.text = text

    def __repr__(self):
        return 'Sentence(%s)' % reprlib.repr(self.text)

    def __iter__(self):
        for match in RE_WORD.finditer(self.text):
            yield match.group()


class GESentence:

    def __init__(self, text):
        self.text = text

    def __repr__(self):
        return 'Sentence(%s)' % reprlib.repr(self.text)

    def __iter__(self):
        return (match.group() for match in RE_WORD.finditer(self.text))


class ArithmeticProgression:

    def __init__(self, begin, step, end=None):
        self.begin = begin
        self.step = step
        self.end = end

    def __iter__(self):
        result = type(self.begin + self.step)(self.begin)
        forever = self.end is None
        index = 0
        while forever or result < self.end:
            yield result
            index += 1
            result = self.begin + self.step * index


def init_iter():
    s = 'ABC'
    s = iter(s)
    while True:
        try:
            print(next(s))
        except StopIteration:
            del s
            break


def gen_123():
    yield 1
    yield 2
    yield 3


def gen_AB():
    # 或是讲学了点新知识，还只是站在使用者的角度，要解决的还是时间和空间效率的问题
    # 这个有点颠覆的认知，如果要想基础知识的话，有点像进程，C里的folk
    # 初步的感觉
    print('start')
    yield 'A'
    print('continue')
    yield 'B'
    print('end')


def aritprog_gen(begin, step, end = None):
    result = type(begin + step)(begin)
    forever = end is None
    index = 0
    while forever or result < end:
        yield result
        index += 1
        result = begin + step * index


def t_aritprog_gen(begin, step, end = None):
    first = type(begin + step)(begin)
    ap_gen = itertools.count(first, step)
    if end is not None:
        ap_gen = itertools.takewhile(lambda n: n < end, ap_gen)
    return ap_gen


def d6():
    return randint(1, 6)


if __name__ == '__main__':
    s = Sentence('"the time has come, " the walrus said')
    s1 = SentenceIterator('hello world')
    s2 = PSentence('hello world')
    s3 = ISentence('hello world')
    s4 = GESentence('hello world')
    # print(next(gen_123()))
    # 生成器表达式
    # res1 = [x*3 for x in gen_AB()]
    # res2 = (x*3 for x in gen_AB())
    ap1 = ArithmeticProgression(1, 0.5, 10)
    ap2 = aritprog_gen(1, 0.5, 10)
    ap3 = t_aritprog_gen(1, 0.5, 10)
    # print(next(ap3))
    d6_iter = iter(d6, 1)
    for rool in d6_iter:
        print(rool)




