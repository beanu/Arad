package com.arad.support.loading;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;

/**
 * һ�����׵��̳߳ع����࣬�ṩ����̳߳�
 */
public class ThreadManager {
	// ����һ��
	private ThreadManager() {
	}

	private static ThreadManager instance = new ThreadManager();

	public static ThreadManager getInstance() {
		return instance;
	}

	public static final String DEFAULT_SINGLE_POOL_NAME = "DEFAULT_SINGLE_POOL_NAME";

	private static ThreadPoolProxy mLongPool = null;
	private static Object mLongLock = new Object();

	private static ThreadPoolProxy mShortPool = null;
	private static Object mShortLock = new Object();

	private static ThreadPoolProxy mDownloadPool = null;
	private static Object mDownloadLock = new Object();

	private static Map<String, ThreadPoolProxy> mMap = new HashMap<String, ThreadPoolProxy>();
	private static Object mSingleLock = new Object();

	/** ��ȡ�����߳� */
	public static ThreadPoolProxy getDownloadPool() {
		synchronized (mDownloadLock) {
			if (mDownloadPool == null) {
				mDownloadPool = new ThreadPoolProxy(3, 3, 5L);
			}
			return mDownloadPool;
		}
	}

	/** ��ȡһ������ִ�г���ʱ������̳߳أ�����Ͷ̺�ʱ������ͬһ�����ж���������Ҫ�Ķ̺�ʱ����ͨ������������� */
	public  static ThreadPoolProxy getLongPool() {
		synchronized (mLongLock) {
			if (mLongPool == null) {
				mLongPool = new ThreadPoolProxy(5, 5, 5L);
			}
			return mLongPool;
		}
	}

	/** ��ȡһ������ִ�ж̺�ʱ������̳߳أ�������Ϊ�ͺ�ʱ����������ͬһ�����ж�ʱ��ò���ִ�У�ͨ������ִ�б��ص�IO/SQL */
	public static ThreadPoolProxy getShortPool() {
		synchronized (mShortLock) {
			if (mShortPool == null) {
				mShortPool = new ThreadPoolProxy(2, 2, 5L);
			}
			return mShortPool;
		}
	}

	/** ��ȡһ�����̳߳أ��������񽫻ᱻ���ռ����˳��ִ�У������ͬ����������� */
	public static ThreadPoolProxy getSinglePool() {
		return getSinglePool(DEFAULT_SINGLE_POOL_NAME);
	}

	/** ��ȡһ�����̳߳أ��������񽫻ᱻ���ռ����˳��ִ�У������ͬ����������� */
	public static ThreadPoolProxy getSinglePool(String name) {
		synchronized (mSingleLock) {
			ThreadPoolProxy singlePool = mMap.get(name);
			if (singlePool == null) {
				singlePool = new ThreadPoolProxy(1, 1, 5L);
				mMap.put(name, singlePool);
			}
			return singlePool;
		}
	}

	public static class ThreadPoolProxy {
		private ThreadPoolExecutor mPool;
		private int mCorePoolSize;
		private int mMaximumPoolSize;
		private long mKeepAliveTime;

		/**
		 * 
		 * @param corePoolSize
		 *            ������ٸ��߳�����
		 * @param maximumPoolSize
		 * @param keepAliveTime
		 */
		private ThreadPoolProxy(int corePoolSize, int maximumPoolSize,
				long keepAliveTime) {
			this.mCorePoolSize = corePoolSize;
			mMaximumPoolSize = maximumPoolSize;
			mKeepAliveTime = keepAliveTime;
		}

		/** ִ�����񣬵��̳߳ش��ڹرգ��������´����µ��̳߳� */
		public synchronized void execute(Runnable run) {
			if (run == null) {
				return;
			}
			// ���mPoolΪnull�ʹ����̳߳�
			if (mPool == null || mPool.isShutdown()) {

				/**
				 * 1.�̳߳�������Թ��?���߳� //
				 * 2.����Ŷ����� ���⿪���߳� //
				 * 3.����̳߳�û��Ҫִ�е������� ���Դ����//
				 * 4.ʱ�䵥λ //
				 * 5.����̳߳��������̶߳��Ѿ����ˣ�ʣ�µ����� ��ʱ�浽LinkedBlockingQueue���Ŷ�//
				 * 6.ThreadFactory��ÿ�δ����µ��̹߳���//
				 * 7.�����߳������mMaximumPoolSizeʱ�������׳��쳣������RejectedExecutionHandler����//
				 * 
				 * ����˵�� ���̳߳��е��߳�С��mCorePoolSize��ֱ�Ӵ����µ��̼߳����̳߳�ִ������
				 * ���̳߳��е��߳���Ŀ����mCorePoolSize���������������������BlockingQueue��
				 * ��BlockingQueue�е���������ˣ����ᴴ���µ��߳�ȥִ�У�
				 * ���ǵ����߳������mMaximumPoolSizeʱ��
				 * �����׳��쳣������RejectedExecutionHandler����
				 * mKeepAliveTime���߳�ִ����������Ҷ�����û�п���ִ�е����񣬴���ʱ�䣬����Ĳ�����ʱ�䵥λ
				 * ThreadFactory��ÿ�δ����µ��̹߳���
				 * 
				 */
				mPool = new ThreadPoolExecutor(mCorePoolSize, mMaximumPoolSize,
						mKeepAliveTime, TimeUnit.MILLISECONDS, // ����
						new LinkedBlockingQueue<Runnable>(),
						Executors.defaultThreadFactory(), new AbortPolicy());
			}
			// �����̳߳�ȥִ���첽����
			mPool.execute(run);
		}
//		isTerminating �߳��Ѿ�ֹͣ
//		isShutdown �߳��Ѿ�����
		/** ȡ���̳߳���ĳ����δִ�е����� */
		public synchronized void cancel(Runnable run) {
			if (mPool != null && (!mPool.isShutdown() || mPool.isTerminating())) {
				mPool.getQueue().remove(run);
			}
		}

		/** ȡ���̳߳���ĳ����δִ�е����� */
		public synchronized boolean contains(Runnable run) {
			if (mPool != null && (!mPool.isShutdown() || mPool.isTerminating())) {
				return mPool.getQueue().contains(run);
			} else {
				return false;
			}
		}
		/** ȡ���̳߳���ĳ������ִ�е����� */
		public synchronized void removeRunning (Runnable run) {
			if (mPool != null && !mPool.isShutdown() &&!mPool.isTerminating()) {
				mPool.remove(run);
			} 
		}
		
		
		/** ȡ���̳߳���ĳ������ִ�е����� */
		public synchronized boolean isRemoveRunning (Runnable run) {
			if (mPool != null && !mPool.isShutdown() &&!mPool.isTerminating()) {
				return mPool.remove(run);
			} else{
				return false;
			}
		}
		/** ���̹ر��̳߳أ���������ִ�е�����Ҳ���ᱻ�ж� */
		public void stop() {
			if (mPool != null && (!mPool.isShutdown() || mPool.isTerminating())) {
				mPool.shutdownNow();
			}
		}

		/** ƽ���رյ������̳߳أ����ǻ�ȷ�������Ѿ���������񶼽��ᱻִ����ϲŹر� */
		public synchronized void shutdown() {
			if (mPool != null && (!mPool.isShutdown() || mPool.isTerminating())) {
				mPool.shutdownNow();
			}
		}
	}
}
