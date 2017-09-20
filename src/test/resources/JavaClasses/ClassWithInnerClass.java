class ClassWithInnerClass {
    int f1;

    int m1() {
        return 0;
    }

    int m2(int a1) {
        return a1;
    }

    int m3(int a1, int a2) {
        return a1 + a2;
    }

    int mo() {
        return 0;
    }

    int mo(int a1) {
        return a1;
    }

    class InnerClass {
        int f1;

        int m1() {
            return 0;
        }

        int m2(int a1) {
            return a1;
        }

        int m3(int a1, int a2) {
            return a1 + a2;
        }

        int mo() {
            return 0;
        }

        int mo(int a1) {
            return a1;
        }
    }
}