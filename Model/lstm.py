import pandas as pd
from pandas import datetime
import matplotlib.pyplot as plt
import numpy as np
from sklearn.preprocessing import StandardScaler
# from keras.models import Sequential
# from keras.layers import Dense, LSTM
from matplotlib import pyplot
from math import sqrt
from sklearn.metrics import mean_squared_error

scaler = StandardScaler()
def load_data():
	'''Load the data and convert them to be suit for LSTM'''
	#load data
	bitcoin = pd.read_csv('Data\\bitcoin.csv', header=0, parse_dates=[0], index_col=0, date_parser=lambda x : datetime.strptime(x,'%Y-%m-%d %I-%p'))
	tweet = pd.read_csv('Data\\tweet1.csv', header=0, parse_dates=[0], index_col=0, date_parser=lambda x : datetime.strptime(x,'%Y-%m-%d %H:%M:%S'))
	bitcoin['Close'].plot()
	pyplot.show()
	#concat two data set
	data = pd.concat([bitcoin['Close'], tweet], axis=1)
	data.fillna(0, inplace = True)
	print(data.head())
	values = data.values
	values = values.astype('float32')
	
	#normalize
	values = scaler.fit_transform(values)
	# print(values)
	# temp = scaler.inverse_transform(values)
	# print(temp)

	#frame
	n_vars = values.shape[1]
	df = pd.DataFrame(values)
	col = list()
	name = list()

	for i in range(1, 0, -1):
		col.append(df.shift(i))
		name += [('x%d(t-%d)' % (j+1, i)) for j in range(n_vars)]

	for i in range(0, 1):
		col.append(df.shift(-i))
		name += [('x%d(t)' % (j+1)) for j in range(n_vars)]

	df = pd.concat(col, axis = 1)
	df.columns = name

	#drop unnecessary column

	print(df.head())
	df.drop(df.columns[[3]], axis = 1, inplace = True)
	df.dropna(inplace=True)
	return df

def predict(train_in, train_out, test_in, test_out):
	# test_in = test_in.reshape((test_in.shape[0], test_in.shape[2]))
	# test_out = test_out.reshape((len(test_out), 1))
	# inv_y = np.concatenate((test_out, test_in[:, 1:]), axis=1)
	# inv_y = scaler.inverse_transform(inv_y)
	# print(inv_y)
	#define model
	model = Sequential()
	model.add(LSTM(10, input_shape=(train_in.shape[1], train_in.shape[2])))
	model.add(Dense(1))
	model.compile(loss='mse', optimizer='adam', metrics=['mean_squared_error'])
	
	history = model.fit(train_in, train_out, epochs = 100, batch_size = 24, validation_data=(test_in, test_out), shuffle = False)
	pyplot.plot(history.history['loss'], label='train')
	pyplot.plot(history.history['val_loss'], label='test')
	pyplot.legend()
	pyplot.show()

	# make a prediction
	yhat = model.predict(test_in)
	test_in = test_in.reshape((test_in.shape[0], test_in.shape[2]))
	# invert scaling for forecast
	inv_yhat = np.concatenate((yhat, test_in[:, 1:]), axis=1)
	inv_yhat = scaler.inverse_transform(inv_yhat)
	inv_yhat = inv_yhat[:,0]
	# invert scaling for actual
	test_out = test_out.reshape((len(test_out), 1))
	inv_y = np.concatenate((test_out, test_in[:, 1:]), axis=1)
	#print(inv_y)
	inv_y = scaler.inverse_transform(inv_y)
	inv_y = inv_y[:,0]
	print(inv_y)
	pyplot.plot(inv_yhat, label = 'predict')
	pyplot.plot(inv_y, label = 'actual')
	pyplot.show()
	# calculate RMSE
	rmse = sqrt(mean_squared_error(inv_y, inv_yhat))
	print('Test RMSE: %.3f' % rmse)

if __name__ == '__main__':
	data = load_data().values

	#split data
	#we will use 20 days for training and 10 days for testing
	n = 24*20
	train = data[:n, :]
	test = data[n:, :]


	#splt the data into input and output and reshape them
	train_input = train[:,:-1]
	train_output = train[:,-1]
	test_input = test[:,:-1]
	test_output = test[:,-1]
	train_input = train_input.reshape((train_input.shape[0], 1, train_input.shape[1]))
	test_input = test_input.reshape((test_input.shape[0], 1, test_input.shape[1]))

	print(train_input.shape, train_output.shape, test_input.shape, test_output.shape)
	#create LSTM
	#predict(train_input, train_output, test_input, test_output)
