package ir.fearefull.wheretoapp.controller.view_controller.base;

import androidx.fragment.app.Fragment;

import java.util.Objects;

public class MyFragment extends Fragment {
    protected String TAG;

    protected void openFragment(Fragment fragment, int rootId) {
        int count = Objects.requireNonNull(getActivity()).getSupportFragmentManager().getBackStackEntryCount() + 1;
        Objects.requireNonNull(Objects.requireNonNull(getActivity()).getSupportFragmentManager())
                .beginTransaction()
                .replace(rootId, fragment, TAG + "_" + count)
                .addToBackStack(null)
                .commit();
    }
}
