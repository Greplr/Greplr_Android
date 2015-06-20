//package com.greplr;
//
///**
// * Created by raghav on 20/06/15.
// */
//public class Test {
//    private final AnimationIcon mEnable =
//            new AnimationIcon(R.drawable.ic_signal_location_enable_animation);
//    private final AnimationIcon mDisable =
//            new AnimationIcon(R.drawable.ic_signal_location_disable_animation);
//
//    private final LocationController mController;
//    private final KeyguardMonitor mKeyguard;
//    private final Callback mCallback = new Callback();
//
//    public LocationTile(Host host) {
//        super(host);
//        mController = host.getLocationController();
//        mKeyguard = host.getKeyguardMonitor();
//    }
//
//    @Override
//    protected BooleanState newTileState() {
//        return new BooleanState();
//    }
//
//    @Override
//    public void setListening(boolean listening) {
//        if (listening) {
//            mController.addSettingsChangedCallback(mCallback);
//            mKeyguard.addCallback(mCallback);
//        } else {
//            mController.removeSettingsChangedCallback(mCallback);
//            mKeyguard.removeCallback(mCallback);
//        }
//    }
//
//    @Override
//    protected void handleClick() {
//        final boolean wasEnabled = (Boolean) mState.value;
//        mController.setLocationEnabled(!wasEnabled);
//        mEnable.setAllowAnimation(true);
//        mDisable.setAllowAnimation(true);
//    }
//
//    @Override
//    protected void handleUpdateState(BooleanState state, Object arg) {
//        final boolean locationEnabled =  mController.isLocationEnabled();
//
//        // Work around for bug 15916487: don't show location tile on top of lock screen. After the
//        // bug is fixed, this should be reverted to only hiding it on secure lock screens:
//        // state.visible = !(mKeyguard.isSecure() && mKeyguard.isShowing());
//        state.visible = !mKeyguard.isShowing();
//        state.value = locationEnabled;
//        if (locationEnabled) {
//            state.icon = mEnable;
//            state.label = mContext.getString(R.string.quick_settings_location_label);
//            state.contentDescription = mContext.getString(
//                    R.string.accessibility_quick_settings_location_on);
//        } else {
//            state.icon = mDisable;
//            state.label = mContext.getString(R.string.quick_settings_location_label);
//            state.contentDescription = mContext.getString(
//                    R.string.accessibility_quick_settings_location_off);
//        }
//    }
//
//    @Override
//    protected String composeChangeAnnouncement() {
//        if (mState.value) {
//            return mContext.getString(R.string.accessibility_quick_settings_location_changed_on);
//        } else {
//            return mContext.getString(R.string.accessibility_quick_settings_location_changed_off);
//        }
//    }
//
//    private final class Callback implements LocationSettingsChangeCallback,
//            KeyguardMonitor.Callback {
//        @Override
//        public void onLocationSettingsChanged(boolean enabled) {
//            refreshState();
//        }
//
//        @Override
//        public void onKeyguardChanged() {
//            refreshState();
//        }
//    };
//}
