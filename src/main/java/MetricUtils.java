import gnu.trove.TIntIntHashMap;

/**
 * Created by besnik on 6/1/15.
 */
public class MetricUtils {
    /**
     * Computes the Euclidean distance between two feature vectors.
     * http://en.wikipedia.org/wiki/Euclidean_distance
     *
     * @param a
     * @param b
     * @return
     */
    public static double getEucledianDistance(int[][] a, int[][] b) {
        TIntIntHashMap a_map = new TIntIntHashMap();
        TIntIntHashMap b_map = new TIntIntHashMap();

        for (int i = 0; i < a.length; i++) {
            a_map.put(a[i][0], a[i][1]);
        }
        for (int i = 0; i < b.length; i++) {
            b_map.put(b[i][0], b[i][1]);
        }

        return getEucledianDistance(a_map, b_map);
    }

    /**
     * Computes the Euclidean distance between two feature vectors.
     * http://en.wikipedia.org/wiki/Euclidean_distance
     *
     * @param a
     * @param b
     * @return
     */
    public static double getEucledianDistance(int[] a, int[] b, int start_index) {
        double rst = 0.0;

        //if the vectors are not of the same lenght.
        if (a.length != b.length) {
            return 0.0;
        }

        for (int i = start_index; i < a.length; i++) {
            rst += Math.pow(a[i] - b[i], 2);
        }

        return Math.sqrt(rst);
    }

    /**
     * Computes the Euclidean distance between two feature vectors.
     * http://en.wikipedia.org/wiki/Euclidean_distance
     *
     * @param a
     * @param b
     * @return
     */
    private static double getEucledianDistance(TIntIntHashMap a, TIntIntHashMap b) {
        double rst = 0.0;

        for (int key : a.keys()) {
            int a_val = a.get(key);
            int b_val = b.containsKey(key) ? b.get(key) : 0;
            rst += Math.pow(a_val - b_val, 2);
        }

        for (int key : b.keys()) {
            int a_val = a.containsKey(key) ? a.get(key) : 0;
            int b_val = b.get(key);
            rst += Math.pow(a_val - b_val, 2);
        }

        return Math.sqrt(rst);
    }

}
