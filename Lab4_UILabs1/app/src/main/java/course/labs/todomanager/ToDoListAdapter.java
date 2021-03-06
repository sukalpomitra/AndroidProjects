package course.labs.todomanager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import course.labs.todomanager.ToDoItem.Status;

public class ToDoListAdapter extends BaseAdapter {

	private final List<ToDoItem> mItems = new ArrayList<ToDoItem>();
	private final Context mContext;
    public final static SimpleDateFormat FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss", Locale.US);

	private static final String TAG = "Lab-UserInterface";

	public ToDoListAdapter(Context context) {

		mContext = context;

	}

	// Add a ToDoItem to the adapter
	// Notify observers that the data set has changed

	public void add(ToDoItem item) {

		mItems.add(item);
		notifyDataSetChanged();

	}

	// Clears the list adapter of all items.

	public void clear() {

		mItems.clear();
		notifyDataSetChanged();

	}

	// Returns the number of ToDoItems

	@Override
	public int getCount() {

		return mItems.size();

	}

	// Retrieve the number of ToDoItems

	@Override
	public Object getItem(int pos) {

		return mItems.get(pos);

	}

	// Get the ID for the ToDoItem
	// In this case it's just the position

	@Override
	public long getItemId(int pos) {

		return pos;

	}

	// TODO - Create a View for the ToDoItem at specified position
    // Remember to check whether convertView holds an already allocated View
    // before created a new View.
    // Consider using the ViewHolder pattern to make scrolling more efficient
    // See: http://developer.android.com/training/improving-layouts/smooth-scrolling.html

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// TODO - Get the current ToDoItem
		final ToDoItem toDoItem = (ToDoItem)getItem(position);

        View toDoView = convertView;
		// TODO - Inflate the View for this ToDoItem
		// from todo_item.xml.
        if (toDoView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            toDoView = (View) inflater.inflate(R.layout.todo_item, null);
        }

            // TODO - Fill in specific ToDoItem data
            // Remember that the data that goes in this View
            // corresponds to the user interface elements defined
            // in the layout file

            // TODO - Display Title in TextView
            final TextView titleText = (TextView)toDoView.findViewById(R.id.titleView);
            titleText.setText(toDoItem.getTitle());
            // TODO - Set up Status CheckBox

            final CheckBox statusView = (CheckBox)toDoView.findViewById(R.id.statusCheckBox);
            if (toDoItem.getStatus() == Status.DONE)
                statusView.setChecked(true);
            else
                statusView.setChecked(false);
            // TODO - Must also set up an OnCheckedChangeListener,
            // which is called when the user toggles the status checkbox

            statusView
                    .setOnCheckedChangeListener(new OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView,
                                                     boolean isChecked) {
                            Log.i(TAG, "Entered onCheckedChanged()");


                        }
                    });

            // TODO - Display Priority in a TextView
            final TextView priority = (TextView)toDoView.findViewById(R.id.priorityView);
            priority.setText(toDoItem.getPriority().toString());

            // TODO - Display Time and Date
            final TextView dateView = (TextView)toDoView.findViewById(R.id.dateView);

            dateView.setText(FORMAT.format(toDoItem.getDate()).toString());


		// Return the View you just created
		return toDoView;

	}
}
