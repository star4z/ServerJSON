import org.json.JSONException;
import org.json.JSONObject;

public class JsonParseTesting {
    public static void main(String[] args) {
        int i = 23;
        double f = 32.43;
        double f2 = 12;
        String s = "foo";
        boolean b = true;
        char c = 'c';
        long l = 100;
        Object o = new Object();

        JSONObject j = new JSONObject();
        j.put("1", i);
        j.put("2", f);
        j.put("3", f2);
        j.put("4", s);
        j.put("5", b);
        j.put("6", c);
        j.put("7", l);
        j.put("8", o);

        boolean[][] parsingTable = new boolean[8][6];

        int a = 0;
        for (String key: j.keySet()){
            try{
                j.getBoolean(key);
                parsingTable[a][0] = true;
            } catch ( JSONException e){
                parsingTable[a][0] = false;
            }

            try{
                j.getInt(key);
                parsingTable[a][1] = true;
            } catch ( JSONException e){
                parsingTable[a][1] = false;
            }

            try{
                j.getLong(key);
                parsingTable[a][2] = true;
            } catch ( JSONException e){
                parsingTable[a][2] = false;
            }

            try{
                j.getDouble(key);
                parsingTable[a][3] = true;
            } catch ( JSONException e){
                parsingTable[a][3] = false;
            }

            try{
                j.getString(key);
                parsingTable[a][4] = true;
            } catch ( JSONException e){
                parsingTable[a][4] = false;
            }

            try{
                j.get(key);
                parsingTable[a][5] = true;
            } catch ( JSONException e){
                parsingTable[a][6] = false;
            }
            a++;
        }

        System.out.println("  b i l d s o");
        char[] valueOrder = new char[]{ 'i', 'd', 'd', 's', 'b', 'c', 'l','o'};
        for (int x = 0; x < 8; x++){
            System.out.print(valueOrder[x]);
            for (int y = 0; y < 6; y++){
                System.out.print(" " + ((parsingTable[x][y])? '✔': ' '));
            }
            System.out.println();
        }

        a = 0;
        for (String key: j.keySet()){

            Object p = j.get(key);
            try{
                ((Boolean) p).booleanValue();
                parsingTable[a][0] = true;
            } catch ( ClassCastException e){
                parsingTable[a][0] = false;
            }

            try{
                ((Integer) p).intValue();
                parsingTable[a][1] = true;
            } catch ( ClassCastException e){
                parsingTable[a][1] = false;
            }

            try{
                ((Long) p).longValue();
                parsingTable[a][2] = true;
            } catch ( ClassCastException e){
                parsingTable[a][2] = false;
            }

            try{
                ((Double) p).doubleValue();
                parsingTable[a][3] = true;
            } catch ( ClassCastException e){
                parsingTable[a][3] = false;
            }

            try{
                ((String) p).toString();
                parsingTable[a][4] = true;
            } catch ( ClassCastException e){
                parsingTable[a][4] = false;
            }

            try{
                ((Object) p).toString();
                parsingTable[a][5] = true;
            } catch ( ClassCastException e){
                parsingTable[a][6] = false;
            }
            a++;
        }

        System.out.println("  b i l d s o");
        for (int x = 0; x < 8; x++){
            System.out.print(valueOrder[x]);
            for (int y = 0; y < 6; y++){
                System.out.print(" " + ((parsingTable[x][y])? '✔': ' '));
            }
            System.out.println();
        }

        a = 0;
        for (String key: j.keySet()){

            Object p = j.get(key);
            try{
                boolean b1 = ((boolean) p);
                parsingTable[a][0] = true;
            } catch ( ClassCastException e){
                parsingTable[a][0] = false;
            }

            try{
                int i1 = ((int) p);
                parsingTable[a][1] = true;
            } catch ( ClassCastException e){
                parsingTable[a][1] = false;
            }

            try{
                long l1 = ((long) p);
                parsingTable[a][2] = true;
            } catch ( ClassCastException e){
                parsingTable[a][2] = false;
            }

            try{
                double d = ((double) p);
                parsingTable[a][3] = true;
            } catch ( ClassCastException e){
                parsingTable[a][3] = false;
            }

            try{
                String e1 =((String) p);
                parsingTable[a][4] = true;
            } catch ( ClassCastException e){
                parsingTable[a][4] = false;
            }

            try{
                Object o1 = ((Object) p);
                parsingTable[a][5] = true;
            } catch ( ClassCastException e){
                parsingTable[a][6] = false;
            }
            a++;
        }

        System.out.println("  b i l d s o");
        for (int x = 0; x < 8; x++){
            System.out.print(valueOrder[x]);
            for (int y = 0; y < 6; y++){
                System.out.print(" " + ((parsingTable[x][y])? '✔': ' '));
            }
            System.out.println();
        }
    }
}
