import contextlib


class LookingGlass:

    def __enter__(self):
        import sys
        self.original_write = sys.stdout.write
        sys.stdout.write = self.reverse_write
        return 'JABBERWOCKY'

    def reverse_write(self, text):
        self.original_write[text[::-1]]

    def __exit__(self, exc_type, exc_val, traceback):
        import sys
        sys.stdout.write = self.original_write
        if exc_type is ZeroDivisionError:
            print('Please DO NOT divide by zero!')
            return True


@contextlib.contextmanager
def looking_glass():
    import sys
    origianl_write = sys.stdout.write

    def reverse_write(text):
        origianl_write(text[::-1])

    sys.stdout.write = reverse_write
    yield 'JABBERWOCKY'
    sys.stdout.write = origianl_write


@contextlib.contextmanager
def looking_glass_2():
    import sys
    origianl_write = sys.stdout.write

    def reverse_write(text):
        origianl_write(text[::-1])

    sys.stdout.write = reverse_write
    msg = ''
    try:
        yield 'JABBERWOCKY'
    except ZeroDivisionError:
        msg = 'Please DO NOT divide by zero!'
    finally:
        sys.stdout.write = origianl_write
        if msg:
            print(msg)


if __name__ == '__main__':
    with looking_glass() as what:
        print('Alice, kitty and Snowdrop')
        print(what)
