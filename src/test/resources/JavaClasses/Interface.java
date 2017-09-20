interface Interface {
    int m1();

    int m2(int a1);

    int m3(int a1, int a2);

    default int m4() {
        return 0;
    }

    default int m5(int a1) {
        return a1;
    }

    default int m6(int a1, int a2) {
        return a1 + a2;
    }

    default int mo() {
        return 0;
    }

    default int mo(int a1) {
        return a1;
    }
}