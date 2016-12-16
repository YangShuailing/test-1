package grammar;

import java.util.Scanner;

public class SwitchString {

    private enum Day {
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, NOVALUE;

        public static Day toDay(String str) {
            try {
                return valueOf(str);
            } catch (Exception ex) {
                return NOVALUE;
            }
        }
    }

    /*
     * http://jun1986.iteye.com/blog/1462637
     * JAVA�е�switch�ж�ԭ���ǲ������ַ���String��Ϊ�����ģ����������
     */
    public static void main(String[] args) {
        @SuppressWarnings("resource")
        Scanner s = new Scanner(System.in);
        String day = s.next();//������ַ���
        switch (Day.toDay(day.toUpperCase())) {
            case SUNDAY:
                System.out.println("������");
                break;
            case MONDAY:
                System.out.println("����һ");
                break;
            case TUESDAY:
                System.out.println("���ڶ�");
                break;
            default:
                break;
        }
        s.close();
    }


}
