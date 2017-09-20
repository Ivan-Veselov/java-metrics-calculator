class ClassWithLocalClass {
    int f1;

    int m0() {
        int l1, l2;
        int l3;

        class LocalClass {
            int f1;

            int m1() {
                int l1, l2;
                int l3;

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

        return 0;
    }

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