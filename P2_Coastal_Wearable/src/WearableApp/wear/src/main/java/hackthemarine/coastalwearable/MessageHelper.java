package hackthemarine.coastalwearable;

import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

public class MessageHelper {

    public Node mNode;

    public void sendMessage(GoogleApiClient client, String Key, String text) {

        if (mNode != null && client!= null && client.isConnected()) {
            Log.d("sendMessage", Boolean.toString(client.isConnected()));
            Wearable.MessageApi.sendMessage(client, mNode.getId(), Key,  text.getBytes()).setResultCallback(

                    new ResultCallback<MessageApi.SendMessageResult>() {
                        @Override
                        public void onResult(MessageApi.SendMessageResult sendMessageResult) {

                            if (!sendMessageResult.getStatus().isSuccess()) {
                                Log.e("sendMessage", "Failed to send message with status code: " + sendMessageResult.getStatus().getStatusCode());
                            }
                        }
                    }
            );
        }
    }

    public void resolveNode(GoogleApiClient client, final OnDone callback) {

        Wearable.NodeApi.getConnectedNodes(client)
                .setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
                    @Override
                    public void onResult(NodeApi.GetConnectedNodesResult nodes) {
                        for (Node node : nodes.getNodes()) {
                            mNode = node;
                        }

                        callback.Done();
                    }
                });
    }

}
