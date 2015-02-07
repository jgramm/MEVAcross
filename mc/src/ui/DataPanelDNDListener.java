package ui;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.io.File;

import javax.swing.JOptionPane;

public class DataPanelDNDListener implements DropTargetListener{

	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dragOver(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dragExit(DropTargetEvent dte) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drop(DropTargetDropEvent dtde) {
		// Accept copy drops
        dtde.acceptDrop(DnDConstants.ACTION_COPY);

        // Get the transfer which can provide the dropped item data
        Transferable transferable = dtde.getTransferable();

        // Get the data formats of the dropped item
        DataFlavor[] flavors = transferable.getTransferDataFlavors();

        // Loop through the flavors
        for (DataFlavor flavor : flavors) {

            try {

                // If the drop items are files
                if (flavor.isFlavorJavaFileListType()) {

                    // Get all of the dropped files
                    @SuppressWarnings("unchecked")
					List<File> files = (List<File>) transferable.getTransferData(flavor);

                    ///***
                    if(files.size() > 1){
                    	JOptionPane.showMessageDialog(null, "MEVA currently supports only a single file as input data");
                    	continue;
                    }
                    // Loop them through
                    for (File file : files) {

                    	DataPanel.load(file.getAbsoluteFile().getAbsolutePath());
                        // Print out the file path
                        System.out.println("File path is '" + file.getPath() + "'.");

                    }

                }

            } catch (Exception e) {

                // Print out the error stack
                e.printStackTrace();

            }
        }

        // Inform that the drop is complete
        dtde.dropComplete(true);
	}

	
	
	
}
