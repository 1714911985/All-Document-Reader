package com.example.alldocunemtreader.utils;

import com.example.alldocunemtreader.R;
import com.example.alldocunemtreader.data.model.DocumentInfo;
import com.example.alldocunemtreader.data.source.local.DocumentInfoDao;

import java.util.List;

/**
 * Author: Eccentric
 * Created on 2024/6/25 16:36.
 * Description: com.example.alldocunemtreader.utils.QueryMethodUtils
 */
public class QueryMethodUtils {
    public static List<DocumentInfo> chooseQueryMethod(DocumentInfoDao filesDao, String filesType, int sortMethodId, int orderMethodId) {
        List<DocumentInfo> filesList = null;
        switch (filesType) {
            case "ALL":
                filesList = chooseALLQueryMethod(filesDao, sortMethodId, orderMethodId);
                break;
            case "PPT":
                filesList = choosePPTQueryMethod(filesDao, sortMethodId, orderMethodId);
                break;
            case "DOC":
                filesList = chooseDOCQueryMethod(filesDao, sortMethodId, orderMethodId);
                break;
            case "XLS":
                filesList = chooseXLSQueryMethod(filesDao, sortMethodId, orderMethodId);
                break;
            case "PDF":
                filesList = choosePDFQueryMethod(filesDao, sortMethodId, orderMethodId);
                break;
            case "TXT":
                filesList = chooseTXTQueryMethod(filesDao, sortMethodId, orderMethodId);
                break;
            case "OTHER":
                filesList = chooseOTHERQueryMethod(filesDao, sortMethodId, orderMethodId);
                break;
            case "FAVORITE":
                filesList = chooseFavoriteQueryMethod(filesDao, sortMethodId, orderMethodId);
                break;
            case "RECENT":
                filesList = chooseRecentQueryMethod(filesDao, sortMethodId, orderMethodId);
                break;
        }
        return filesList;
    }

    private static List<DocumentInfo> chooseALLQueryMethod(DocumentInfoDao filesDao, int sortMethodId, int orderMethodId) {
        if (orderMethodId == R.id.bdrb_asc) {
            if (sortMethodId == R.id.bdrb_name) {
                return filesDao.getALLDocumentInfoSortByFileNameAscending();
            } else if (sortMethodId == R.id.bdrb_date) {
                return filesDao.getALLDocumentInfoSortByCreatedTimeAscending();
            } else if (sortMethodId == R.id.bdrb_type) {
                return filesDao.getALLDocumentInfoSortByFileTypeAscending();
            } else if (sortMethodId == R.id.bdrb_size) {
                return filesDao.getALLDocumentInfoSortByFileSizeAscending();
            }
        } else if (orderMethodId == R.id.bdrb_desc) {
            if (sortMethodId == R.id.bdrb_name) {
                return filesDao.getALLDocumentInfoSortByFileNameDescending();
            } else if (sortMethodId == R.id.bdrb_date) {
                return filesDao.getALLDocumentInfoSortByCreatedTimeDescending();
            } else if (sortMethodId == R.id.bdrb_type) {
                return filesDao.getALLDocumentInfoSortByFileTypeDescending();
            } else if (sortMethodId == R.id.bdrb_size) {
                return filesDao.getALLDocumentInfoSortByFileSizeDescending();
            }
        }
        return null;
    }

    private static List<DocumentInfo> choosePPTQueryMethod(DocumentInfoDao filesDao, int sortMethodId, int orderMethodId) {
        if (orderMethodId == R.id.bdrb_asc) {
            if (sortMethodId == R.id.bdrb_name) {
                return filesDao.getPPTDocumentInfoSortByFileNameAscending();
            } else if (sortMethodId == R.id.bdrb_date) {
                return filesDao.getPPTDocumentInfoSortByCreatedTimeAscending();
            } else if (sortMethodId == R.id.bdrb_type) {
                return filesDao.getPPTDocumentInfoSortByFileTypeAscending();
            } else if (sortMethodId == R.id.bdrb_size) {
                return filesDao.getPPTDocumentInfoSortByFileSizeAscending();
            }
        } else if (orderMethodId == R.id.bdrb_desc) {
            if (sortMethodId == R.id.bdrb_name) {
                return filesDao.getPPTDocumentInfoSortByFileNameDescending();
            } else if (sortMethodId == R.id.bdrb_date) {
                return filesDao.getPPTDocumentInfoSortByCreatedTimeDescending();
            } else if (sortMethodId == R.id.bdrb_type) {
                return filesDao.getPPTDocumentInfoSortByFileTypeDescending();
            } else if (sortMethodId == R.id.bdrb_size) {
                return filesDao.getPPTDocumentInfoSortByFileSizeDescending();
            }
        }
        return null;
    }

    private static List<DocumentInfo> chooseDOCQueryMethod(DocumentInfoDao filesDao, int sortMethodId, int orderMethodId) {
        if (orderMethodId == R.id.bdrb_asc) {
            if (sortMethodId == R.id.bdrb_name) {
                return filesDao.getDOCDocumentInfoSortByFileNameAscending();
            } else if (sortMethodId == R.id.bdrb_date) {
                return filesDao.getDOCDocumentInfoSortByCreatedTimeAscending();
            } else if (sortMethodId == R.id.bdrb_type) {
                return filesDao.getDOCDocumentInfoSortByFileTypeAscending();
            } else if (sortMethodId == R.id.bdrb_size) {
                return filesDao.getDOCDocumentInfoSortByFileSizeAscending();
            }
        } else if (orderMethodId == R.id.bdrb_desc) {
            if (sortMethodId == R.id.bdrb_name) {
                return filesDao.getDOCDocumentInfoSortByFileNameDescending();
            } else if (sortMethodId == R.id.bdrb_date) {
                return filesDao.getDOCDocumentInfoSortByCreatedTimeDescending();
            } else if (sortMethodId == R.id.bdrb_type) {
                return filesDao.getDOCDocumentInfoSortByFileTypeDescending();
            } else if (sortMethodId == R.id.bdrb_size) {
                return filesDao.getDOCDocumentInfoSortByFileSizeDescending();
            }
        }
        return null;
    }

    private static List<DocumentInfo> chooseXLSQueryMethod(DocumentInfoDao filesDao, int sortMethodId, int orderMethodId) {
        if (orderMethodId == R.id.bdrb_asc) {
            if (sortMethodId == R.id.bdrb_name) {
                return filesDao.getXLSDocumentInfoSortByFileNameAscending();
            } else if (sortMethodId == R.id.bdrb_date) {
                return filesDao.getXLSDocumentInfoSortByCreatedTimeAscending();
            } else if (sortMethodId == R.id.bdrb_type) {
                return filesDao.getXLSDocumentInfoSortByFileTypeAscending();
            } else if (sortMethodId == R.id.bdrb_size) {
                return filesDao.getXLSDocumentInfoSortByFileSizeAscending();
            }
        } else if (orderMethodId == R.id.bdrb_desc) {
            if (sortMethodId == R.id.bdrb_name) {
                return filesDao.getXLSDocumentInfoSortByFileNameDescending();
            } else if (sortMethodId == R.id.bdrb_date) {
                return filesDao.getXLSDocumentInfoSortByCreatedTimeDescending();
            } else if (sortMethodId == R.id.bdrb_type) {
                return filesDao.getXLSDocumentInfoSortByFileTypeDescending();
            } else if (sortMethodId == R.id.bdrb_size) {
                return filesDao.getXLSDocumentInfoSortByFileSizeDescending();
            }
        }
        return null;
    }

    private static List<DocumentInfo> choosePDFQueryMethod(DocumentInfoDao filesDao, int sortMethodId, int orderMethodId) {
        if (orderMethodId == R.id.bdrb_asc) {
            if (sortMethodId == R.id.bdrb_name) {
                return filesDao.getPDFDocumentInfoSortByFileNameAscending();
            } else if (sortMethodId == R.id.bdrb_date) {
                return filesDao.getPDFDocumentInfoSortByCreatedTimeAscending();
            } else if (sortMethodId == R.id.bdrb_type) {
                return filesDao.getPDFDocumentInfoSortByFileTypeAscending();
            } else if (sortMethodId == R.id.bdrb_size) {
                return filesDao.getPDFDocumentInfoSortByFileSizeAscending();
            }
        } else if (orderMethodId == R.id.bdrb_desc) {
            if (sortMethodId == R.id.bdrb_name) {
                return filesDao.getPDFDocumentInfoSortByFileNameDescending();
            } else if (sortMethodId == R.id.bdrb_date) {
                return filesDao.getPDFDocumentInfoSortByCreatedTimeDescending();
            } else if (sortMethodId == R.id.bdrb_type) {
                return filesDao.getPDFDocumentInfoSortByFileTypeDescending();
            } else if (sortMethodId == R.id.bdrb_size) {
                return filesDao.getPDFDocumentInfoSortByFileSizeDescending();
            }
        }
        return null;
    }

    private static List<DocumentInfo> chooseTXTQueryMethod(DocumentInfoDao filesDao, int sortMethodId, int orderMethodId) {
        if (orderMethodId == R.id.bdrb_asc) {
            if (sortMethodId == R.id.bdrb_name) {
                return filesDao.getTXTDocumentInfoSortByFileNameAscending();
            } else if (sortMethodId == R.id.bdrb_date) {
                return filesDao.getTXTDocumentInfoSortByCreatedTimeAscending();
            } else if (sortMethodId == R.id.bdrb_type) {
                return filesDao.getTXTDocumentInfoSortByFileTypeAscending();
            } else if (sortMethodId == R.id.bdrb_size) {
                return filesDao.getTXTDocumentInfoSortByFileSizeAscending();
            }
        } else if (orderMethodId == R.id.bdrb_desc) {
            if (sortMethodId == R.id.bdrb_name) {
                return filesDao.getTXTDocumentInfoSortByFileNameDescending();
            } else if (sortMethodId == R.id.bdrb_date) {
                return filesDao.getTXTDocumentInfoSortByCreatedTimeDescending();
            } else if (sortMethodId == R.id.bdrb_type) {
                return filesDao.getTXTDocumentInfoSortByFileTypeDescending();
            } else if (sortMethodId == R.id.bdrb_size) {
                return filesDao.getTXTDocumentInfoSortByFileSizeDescending();
            }
        }
        return null;
    }

    private static List<DocumentInfo> chooseOTHERQueryMethod(DocumentInfoDao filesDao, int sortMethodId, int orderMethodId) {
        if (orderMethodId == R.id.bdrb_asc) {
            if (sortMethodId == R.id.bdrb_name) {
                return filesDao.getOTHERDocumentInfoSortByFileNameAscending();
            } else if (sortMethodId == R.id.bdrb_date) {
                return filesDao.getOTHERDocumentInfoSortByCreatedTimeAscending();
            } else if (sortMethodId == R.id.bdrb_type) {
                return filesDao.getOTHERDocumentInfoSortByFileTypeAscending();
            } else if (sortMethodId == R.id.bdrb_size) {
                return filesDao.getOTHERDocumentInfoSortByFileSizeAscending();
            }
        } else if (orderMethodId == R.id.bdrb_desc) {
            if (sortMethodId == R.id.bdrb_name) {
                return filesDao.getOTHERDocumentInfoSortByFileNameDescending();
            } else if (sortMethodId == R.id.bdrb_date) {
                return filesDao.getOTHERDocumentInfoSortByCreatedTimeDescending();
            } else if (sortMethodId == R.id.bdrb_type) {
                return filesDao.getOTHERDocumentInfoSortByFileTypeDescending();
            } else if (sortMethodId == R.id.bdrb_size) {
                return filesDao.getOTHERDocumentInfoSortByFileSizeDescending();
            }
        }
        return null;
    }

    private static List<DocumentInfo> chooseRecentQueryMethod(DocumentInfoDao filesDao, int sortMethodId, int orderMethodId) {
        if (orderMethodId == R.id.bdrb_asc) {
            if (sortMethodId == R.id.bdrb_name) {
                return filesDao.getRecentDocumentInfoSortByFileNameAscending();
            } else if (sortMethodId == R.id.bdrb_date) {
                return filesDao.getRecentDocumentInfoSortByCreatedTimeAscending();
            } else if (sortMethodId == R.id.bdrb_type) {
                return filesDao.getRecentDocumentInfoSortByFileTypeAscending();
            } else if (sortMethodId == R.id.bdrb_size) {
                return filesDao.getRecentDocumentInfoSortByFileSizeAscending();
            }
        } else if (orderMethodId == R.id.bdrb_desc) {
            if (sortMethodId == R.id.bdrb_name) {
                return filesDao.getRecentDocumentInfoSortByFileNameDescending();
            } else if (sortMethodId == R.id.bdrb_date) {
                return filesDao.getRecentDocumentInfoSortByCreatedTimeDescending();
            } else if (sortMethodId == R.id.bdrb_type) {
                return filesDao.getRecentDocumentInfoSortByFileTypeDescending();
            } else if (sortMethodId == R.id.bdrb_size) {
                return filesDao.getRecentDocumentInfoSortByFileSizeDescending();
            }
        }
        return null;
    }

    private static List<DocumentInfo> chooseFavoriteQueryMethod(DocumentInfoDao filesDao, int sortMethodId, int orderMethodId) {
        if (orderMethodId == R.id.bdrb_asc) {
            if (sortMethodId == R.id.bdrb_name) {
                return filesDao.getFavoriteDocumentInfoSortByFileNameAscending();
            } else if (sortMethodId == R.id.bdrb_date) {
                return filesDao.getFavoriteDocumentInfoSortByCreatedTimeAscending();
            } else if (sortMethodId == R.id.bdrb_type) {
                return filesDao.getFavoriteDocumentInfoSortByFileTypeAscending();
            } else if (sortMethodId == R.id.bdrb_size) {
                return filesDao.getFavoriteDocumentInfoSortByFileSizeAscending();
            }
        } else if (orderMethodId == R.id.bdrb_desc) {
            if (sortMethodId == R.id.bdrb_name) {
                return filesDao.getFavoriteDocumentInfoSortByFileNameDescending();
            } else if (sortMethodId == R.id.bdrb_date) {
                return filesDao.getFavoriteDocumentInfoSortByCreatedTimeDescending();
            } else if (sortMethodId == R.id.bdrb_type) {
                return filesDao.getFavoriteDocumentInfoSortByFileTypeDescending();
            } else if (sortMethodId == R.id.bdrb_size) {
                return filesDao.getFavoriteDocumentInfoSortByFileSizeDescending();
            }
        }
        return null;
    }

}