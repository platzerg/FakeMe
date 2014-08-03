package com.platzerworld.fakefb.utils.actions;

import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.platzerworld.fakefb.utils.Errors;
import com.platzerworld.fakefb.utils.Errors.ErrorMsg;
import com.platzerworld.fakefb.utils.Logger;
import com.platzerworld.fakefb.utils.fb.SessionManager;
import com.platzerworld.fakefb.utils.listeners.OnDeleteListener;

public class DeleteRequestAction extends AbstractAction {

	private OnDeleteListener mOnDeleteListener;
	private String mRequestId;

	public DeleteRequestAction(SessionManager sessionManager) {
		super(sessionManager);
	}

	public void setRequestId(String requestId) {
		mRequestId = requestId;
	}

	public void setOnDeleteListener(OnDeleteListener onDeleteRequestListener) {
		mOnDeleteListener = onDeleteRequestListener;
	}

	@Override
	protected void executeImpl() {
		if (sessionManager.isLogin(true)) {
			Session session = sessionManager.getActiveSession();
			Request request = new Request(session, mRequestId, null, HttpMethod.DELETE, new Request.Callback() {
				@Override
				public void onCompleted(Response response) {
					FacebookRequestError error = response.getError();
					if (error != null) {
						Logger.logError(DeleteRequestAction.class, "failed to delete requests", error.getException());
						if (mOnDeleteListener != null) {
							mOnDeleteListener.onException(error.getException());
						}
					}
					else {
						if (mOnDeleteListener != null) {
							mOnDeleteListener.onComplete(null);
						}
					}
				}
			});
			Request.executeBatchAsync(request);
		}
		else {
			String reason = Errors.getError(ErrorMsg.LOGIN);
			Logger.logError(DeleteRequestAction.class, reason, null);
			if (mOnDeleteListener != null) {
				mOnDeleteListener.onFail(reason);
			}
		}
	}

}
