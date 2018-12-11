import numpy as np
import pandas as pd

filename = 'temp.csv'
f1 = pd.read_csv(filename) 
filename2 = 'Bcoin.csv'
f2 = pd.read_csv(filename2) 

selected_row = f1.score.notna()
f1_slc = f1[selected_row].reset_index()
f2_slc = f2[selected_row].reset_index()
arr = np.correlate(f2_slc['Close'],f1_slc['score'], 'same')

import matplotlib.pyplot as plt
import time
import datetime
t = []
f1_slc.reset_index()
for i in range(len(f1_slc["time"])):
    t.append(time.mktime(datetime.datetime.strptime(f1_slc["time"][i], "%Y-%m-%d %H:%M:%S").timetuple())) 
t2 = (np.array(t)-t[0])/3600
t3 = []
t3 = list(range(1, 766))
plt.plot(t3,arr)
plt.show()

