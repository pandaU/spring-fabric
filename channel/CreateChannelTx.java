package application.java.channel;

import java.io.IOException;
import java.io.InputStream;


/**
 * <p>
 * The type Create channel tx.
 *
 * @author XieXiongXiong
 * @date 2021 -07-09
 */
public class CreateChannelTx {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @author XieXiongXiong
     * @date 2021 -07-09 09:04:59
     */
    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();
        String cmd = "/usr/local/src/xxzx-fabric/bin/configtxgen -configPath /usr/local/src/xxzx-fabric/xxzx-singleton-network/configtx -profile TwoOrgsChannel -outputCreateChannelTx /usr/local/channel-artifacts/"+args[0]+".tx -channelID " + args[0];
        try {
            Process process = runtime.exec(cmd);
            InputStream in = process.getErrorStream();
            byte[] bytes = new byte[1024];
            int index = 0;
            StringBuffer sb = new StringBuffer();
            while ((index = in.read(bytes)) != -1){
                sb.append(new String(bytes, "utf8"));
            }
            System.out.println(sb);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
