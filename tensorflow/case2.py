import tensorflow as tf
# Define an arbitrary matrix
matrix = tf.constant([[1., 2.]])
# Run the negation operator on it
neg_matrix = tf.neg(matrix)

# Start a session to be able to run operations
with tf.Session() as sess:
# Tell the session to evaluate negMatrix
	result = sess.run(neg_matrix)
# Print the resulting matrix
print(result)