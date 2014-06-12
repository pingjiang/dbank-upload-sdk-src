package com.huawei.cloud.dbank.service.impl;

import com.huawei.cloud.dbank.DBankException;
import com.huawei.cloud.dbank.ListOption;
import com.huawei.cloud.dbank.service.FSService;
import junit.framework.TestCase;

public class DBankFSServiceTest extends TestCase {

    private FSService fsService;

    public DBankFSServiceTest() throws DBankException {
        fsService = new DBankFSService("opendocs");
        String access_token = "BFH4bl39lJYDBFUVa7wU+dVgN+fShbnF7o+2uG2g/YiM/V05JDbIQHpqyR0=";
        fsService.setAccess_token(access_token);
    }

    public void testHandle() throws DBankException {

    }

    /**
     * http://api.dbank.com/rest.php?nsp_svc=nsp.vfs.lsdir&path=%2Fapp%2Fopendocs&fields=%5B%22name%22%2C%22size%22%2C%22type%22%2C%22dirCount%22%2C%22fileCount%22%2C%22dbank_systemType%22%2C%22dbank_isShared%22%2C%22modifyTime%22%2C%22dbank_status%22%2C%22dbank_srcpath%22%2C%22dbank_srcname%22%2C%22accessTime%22%2C%22url%22%5D&type=3&nsp_sid=muuAicAuunY5wuTCqgWuaEPkLYvcqKVHgTx5ntGD81kdSAQb&nsp_ts=1402568562126&nsp_key=4A7B129EDE3DF5029694FA6049E0FBA7&nsp_fmt=JS&nsp_cb=_jqjsp
     *
     * @throws DBankException
     */
    public void testListDir() throws DBankException {
        String[] fields = { "name","size","type","dirCount","fileCount","dbank_systemType","dbank_isShared","modifyTime","dbank_status","dbank_srcpath","dbank_srcname","accessTime","url" };
        //fsService.listDir("/app/opendocs", fields, null);
    }

    public void testCopyFile() throws DBankException {

    }

    public void testMoveFile() throws DBankException {

    }

    public void testRemoveFile() throws DBankException {

    }

    public void testGetAttr() throws DBankException {

    }

    public void testSetAttr() throws DBankException {

    }
}