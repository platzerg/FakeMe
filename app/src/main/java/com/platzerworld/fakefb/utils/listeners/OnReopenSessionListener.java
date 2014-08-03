package com.platzerworld.fakefb.utils.listeners;

import com.platzerworld.fakefb.utils.fb.Permission;


public interface OnReopenSessionListener {
    void onSuccess();

    void onNotAcceptingPermissions(Permission.Type type);
}
