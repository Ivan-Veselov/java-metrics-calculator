interface Interface {
    default int method0() {
        return 0;
    }
}

abstract class Methods {
    public static void method1() {
    }

    private int method2() {
        return new Interface() {
            public int method0() {
                return 1 - 1;
            }
        }.method0();
        return 0;
    }

    protected final float method3() {
        return 3.14f;
    }

    public abstract void method4();

    private static class InnerClass {
        public void method5() {
        }
    }
}

class HiddenClass {
    public Integer method6() {
        class MethodClass {
            private int method7() {
                return -1;
            }
        }

        return 256;
    }
}
