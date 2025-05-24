package dev.mikoto2000.study.sns.bluesky;

import java.util.List;

import work.socialhub.kbsky.BlueskyFactory;
import work.socialhub.kbsky.api.app.bsky.FeedResource;
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedGetTimelineRequest;
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedGetTimelineResponse;
import work.socialhub.kbsky.api.entity.com.atproto.server.ServerCreateSessionRequest;
import work.socialhub.kbsky.api.entity.share.Response;
import work.socialhub.kbsky.auth.BearerTokenAuthProvider;
import work.socialhub.kbsky.model.app.bsky.feed.FeedDefsFeedViewPost;
import work.socialhub.kbsky.model.app.bsky.feed.FeedPost;

/**
 * Hello world!
 */
public class App {
  public static void main(String[] args) {
    System.out.println("Hello World!");

    ServerCreateSessionRequest request = new ServerCreateSessionRequest();
    request.setIdentifier("xxxxxxxxxxxxxxxxxxxxxx");
    request.setPassword("yyyyyyyyyyyyyyyyyyy");

    var client = BlueskyFactory.INSTANCE.instance("https://bsky.social");

    var session = client.server().createSession(request).getData();

    var accessJwt = session.getAccessJwt();

    System.out.println("Login successeed: " + session.getHandle());

    // タイムライン取得
    FeedResource feedApi = client.feed();

    BearerTokenAuthProvider auth = new BearerTokenAuthProvider(accessJwt, null);

    FeedGetTimelineRequest timelineRequest = new FeedGetTimelineRequest(auth);

    Response<FeedGetTimelineResponse> response = feedApi.getTimeline(timelineRequest);

    List<FeedDefsFeedViewPost> posts = response.getData().getFeed();

    for (FeedDefsFeedViewPost post : posts) {
      var record = post.getPost().getRecord();
      if (record instanceof FeedPost) {
      System.out.println("📨 投稿: " + ((FeedPost)record).getText());
      }
    }

    System.out.println("終了！");
    System.exit(0);
  }
}
