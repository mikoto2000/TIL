use feed_rs::parser;

// copy from https://mozaic.fm
const RSS: &'static str = "
<?xml version=\"1.0\" encoding=\"UTF-8\"?>
<rss version=\"2.0\" xmlns:atom=\"http://www.w3.org/2005/Atom\" xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xmlns:itunes=\"http://www.itunes.com/dtds/podcast-1.0.dtd\" xmlns:media=\"http://search.yahoo.com/mrss/\" xmlns:googleplay=\"http://www.google.com/schemas/play-podcasts/1.0\">
  <channel>
    <title>mozaic.fm</title>
    <link>http://mozaic.fm/</link>
    <description>next generation web podcast</description>
    <generator>Ruby</generator>
    <language>ja</language>
    <copyright>Copyright (c) 2014 mozaic.fm. All Rights Reserved. Redistribute, Transcript are not allowed.</copyright>
    <atom:link xmlns:atom=\"http://www.w3.org/2005/Atom\" rel=\"self\" type=\"application/rss+xml\" href=\"http://feed.mozaic.fm\" />
    <itunes:author>Jxck</itunes:author>
    <itunes:category text=\"Technology\" />
    <itunes:explicit>no</itunes:explicit>
    <itunes:image href=\"https://mozaic.fm/assets/img/mozaic.jpeg\" />
    <itunes:keywords>web,technology,programming,it,software,jxck</itunes:keywords>
    <itunes:subtitle>next generation web podcast</itunes:subtitle>
    <itunes:summary>talking about next generation web technologies hosted by Jxck</itunes:summary>
    <media:category scheme=\"http://www.itunes.com/dtds/podcast-1.0.dtd\">Technology/Podcasting</media:category>
    <media:copyright>Copyright (c) 2014 mozaic.fm. All Rights Reserved. Redistribute, Transcript are not allowed.</media:copyright>
    <media:credit role=\"author\">Jxck</media:credit>
    <media:description type=\"plain\">next generation web podcast</media:description>
    <media:keywords>web,technology,programming,it,software,jxck</media:keywords>
    <media:rating>nonadult</media:rating>
    <media:thumbnail url=\"https://mozaic.fm/assets/img/mozaic.jpeg\" />
    <itunes:owner>
      <itunes:email>mail@jxck.io</itunes:email>
      <itunes:name>Jxck</itunes:name>
    </itunes:owner>

    <item>
      <category>mozaicfm</category>
      <enclosure url=\"http://files.mozaic.fm/mozaic-ep153.mp3\" length=\"83316597\" type=\"audio/mpeg\" />
      <guid isPermaLink=\"false\">https://mozaic.fm/episodes/153/mozaic-ecosystem-202406.html</guid>
      <link>https://mozaic.fm/episodes/153/mozaic-ecosystem-202406.html</link>
      <pubDate>Tue, 11 Jun 2024 00:00:00 +0900</pubDate>
      <title>ep153 Monthly Ecosystem 202406 | mozaic.fm</title>
      <itunes:author>Jxck</itunes:author>
      <itunes:duration>01:55:15</itunes:duration>
      <itunes:explicit>no</itunes:explicit>
      <itunes:keywords>web,tech,it</itunes:keywords>
      <itunes:order>0</itunes:order>
      <itunes:subtitle>第 153 回のテーマは 2024 年 6 月の Monthly Ecosystem です。</itunes:subtitle>
      <media:content url=\"https://files.mozaic.fm/mozaic-ep153.mp3\" fileSize=\"83316597\" type=\"audio/mpeg\" />
      <description>
第 153 回のテーマは 2024 年 6 月の Monthly Ecosystem です。(冒頭で間違えて 5 月って言ってた。)

Show Note はこちら: https://mozaic.fm/episodes/153/mozaic-ecosystem-202406.html
      </description>
    </item>

    <item>
      <category>mozaicfm</category>
      <enclosure url=\"http://files.mozaic.fm/mozaic-ep152.mp3\" length=\"14125971\" type=\"audio/mpeg\" />
      <guid isPermaLink=\"false\">https://mozaic.fm/episodes/152/mozaic-renewal-202406.html</guid>
      <link>https://mozaic.fm/episodes/152/mozaic-renewal-202406.html</link>
      <pubDate>Tue, 04 Jun 2024 00:00:00 +0900</pubDate>
      <title>ep152 mozaic.fm Renewal Project 202406 | mozaic.fm</title>
      <itunes:author>Jxck</itunes:author>
      <itunes:duration>00:19:09</itunes:duration>
      <itunes:explicit>no</itunes:explicit>
      <itunes:keywords>web,tech,it</itunes:keywords>
      <itunes:order>1</itunes:order>
      <itunes:subtitle>第 152 回のテーマは 2024 年 6 月の mozaic.fm renewal project です。</itunes:subtitle>
      <media:content url=\"https://files.mozaic.fm/mozaic-ep152.mp3\" fileSize=\"14125971\" type=\"audio/mpeg\" />
      <description>
第 152 回のテーマは 2024 年 6 月の mozaic.fm renewal project です。

Show Note はこちら: https://mozaic.fm/episodes/152/mozaic-renewal-202406.html
      </description>
    </item>

  </channel>
</rss>
";

fn main() {
    // RSS のパース
    let feed = parser::parse(RSS.as_bytes()).unwrap();

    // タイトル表示
    println!("channel title: {}", feed.title.unwrap().content);
    println!();

    // エピソード表示
    feed.entries.iter().for_each(|entry| {
        println!("\tepisode title: {}", entry.clone().title.unwrap().content);
        println!("\tepisode media url: {}", entry.media[0].content[0].url.clone().unwrap().as_str());
        println!();
    });
}

