#!/usr/bin/python
# -*- coding: utf-8 -*-

# topic_pipe
#
# pipe_test_sub std_msgs/String を購読し、トピックを受信したら
# pipe_test_pub std_msgs/String に出版する。
import rospy
from std_msgs.msg import String

# 出版トピック定義
pub = rospy.Publisher('pipe_test_pub', String, queue_size=10)


def callback(data):
    rospy.loginfo(rospy.get_caller_id() + 'I heard %s', data.data)
    pub.publish(data.data)


def pipe():
    rospy.Subscriber('pipe_test_sub', String, callback)
    rospy.spin()


if __name__ == '__main__':
    # ノード初期化
    rospy.init_node('topic_pipe', anonymous=True)

    pipe()

