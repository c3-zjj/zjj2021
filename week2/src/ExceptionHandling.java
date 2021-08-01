public class ExceptionHandling {
    public static void main(String[] args) {
        int i ;
            try{
                int j=1;
                for (i = 1; ; i++) {
                    j=120/(120-i);
                }
            }catch (Exception e)
            {
                System.out.println("数值超过限定");
            };

    }
}
