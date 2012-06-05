Communication Commands 2 (System of Commands Complying to the IEEE 488.2-1992 Standard)

Before Programming

Environment
  Model      : IBM-compatible PC
  Language   : Visual Basic Ver5.0 Professional Edition or later.
  GP-IB board: AT-GPIB/TNT IEEE-488.2 by National Instruments.

Settings on Visual Basic
  Standard modules used: Niglobal.bas
                         Vbib-32.bas

WT210/WT230 Settings
  GP-IB address
      The sample programs given in this chapter use a GP-IB address of 1 for the
      WT210/WT230.
      Set the GP-IB address to 1 according to the procedures described in section 10.5.


***************************************************************
Sample Program (Initialization, Error, and Execution Functions)
***************************************************************

---------------------------------------------------------------------------------------------------
Option Explicit
Dim StartFlag As Integer                           'Start Flag
Dim addr As Integer                                'GPIB Address
Dim Timeout As Integer                             'Timeout
Dim Dev As Integer                                 'Device ID(GPIB)
Dim term As String                                 'Terminator
Dim Query(1100) As String                          'Query String
Dim Dummy As Integer
---------------------------------------------------------------------------------------------------

Private Function InitGpib() As Integer
    Dim eos As Integer                             'EOS
    Dim eot As Integer                             'EOI
    Dim brd As Integer                             'GPIB Board ID
    Dim sts As Integer

    eos = &HC0A                                    'Terminator = LF
    eot = 1                                        'EOI = Enable
    term = Chr(10)
    Timeout = T10s                                 'Timeout = 10s

    brd = ilfind("GPIB0")
    If (brd < 0) Then
        GoTo GPIBError
    End If
    Dev = ildev(0, addr, 0, Timeout, eot, eos)
    If (Dev < 0) Then
        GoTo GPIBError
    End If
    sts = ilsic(brd)                               'Set IFC
    If (sts < 0) Then
        Call DisplayGPIBError(sts, "ilsic")
        InitGpib = 1
        Exit Function
    End If
    InitGpib = 0
    Exit Function

GPIBError:
        Call DisplayGPIBError(sts, "ilsic")
        InitGpib = 1
End Function
---------------------------------------------------------------------------------------------------

Private Sub DisplayGPIBError(ByVal sts As Integer, ByVal msg As String)
    Dim wrn As String
    Dim ers As String
    Dim ern As Integer
    
    If (sts And TIMO) Then
        wrn = "Time out" + Chr(13)
    Else
        wrn = ""
    End If
    If (sts And EERR) Then
        ern = iberr
        If (ern = EDVR) Then
            ers = "EDVR:System error"
        ElseIf (ern = ECIC) Then
            ers = "ECIC:Function requires GPIB board to be CIC"
        ElseIf (ern = ENOL) Then
            ers = "ENOL:No Listeners on the GPIB"
        ElseIf (ern = EADR) Then
            ers = "EADR:GPIB board not addressed correctly"
        ElseIf (ern = EARG) Then
            ers = "EARG:Invalid argument to function call"
        ElseIf (ern = ESAC) Then
            ers = "ESAC:GPIB board not System Controller as required"
        ElseIf (ern = EABO) Then
            ers = "EABO:I/O operation aborted(timeout)"
        ElseIf (ern = ENEB) Then
            ers = "ENEB:Nonexistent GPIB board"
        ElseIf (ern = EDMA) Then
            ers = "EDMA:DMA error"
        ElseIf (ern = EOIP) Then
            ers = "EOIP:I/O operation started before previous operation completed"
        ElseIf (ern = ECAP) Then
            ers = "ECAP:No capability for intended operation"
        ElseIf (ern = EFSO) Then
            ers = "EFSO:File system operation error"
        ElseIf (ern = EBUS) Then
            ers = "EBUS:GPIB bus error"
        ElseIf (ern = ESTB) Then
            ers = "ESTB:Serial poll status byte queue overflow"
        ElseIf (ern = ESRQ) Then
            ers = "ESRQ:SRQ remains asserted"
        ElseIf (ern = ETAB) Then
            ers = "ETAB:The return buffer is full"
        ElseIf (ern = ELCK) Then
            ers = "ELCK:Address or board is locked"
        Else
            ers = ""
        End If
    Else
        ers = ""
    End If

    MsgBox ("Status No. " + Str(sts) + Chr(13) + wrn + "Error No. " + Str(ern) + Chr(13) + ers + Chr(13) + msg), vbExclamation, "Error!"
    Call ibonl(Dev, 0)
    Dev = -1
End Sub
---------------------------------------------------------------------------------------------------

Private Sub Command1_Click()
    Dim sts As Integer

    If (StartFlag = 1) Then
        Exit Sub
    End If
    StartFlag = 1
    Text1.Text = "START"
    List1.Clear
    Dummy = DoEvents()
    sts = GpibNormal                           'Run Sample1(GPIB) Get Normal Data
    If (sts = 0) Then
        Text1.Text = "END"
    Else
        Text1.Text = "ERROR"
    End If
    StartFlag = 0
End Sub
---------------------------------------------------------------------------------------------------

Private Sub Command2_Click()
    Dim sts As Integer

    If (StartFlag = 1) Then
        Exit Sub
    End If
    StartFlag = 1
    Text1.Text = "START"
    List1.Clear
    Dummy = DoEvents()
    sts = GpibHarmonics                        'Run Sample2(GPIB) Get Harmonics Data
    If (sts = 0) Then
        Text1.Text = "END"
    Else
        Text1.Text = "ERROR"
    End If
    StartFlag = 0
End Sub
---------------------------------------------------------------------------------------------------

Private Sub Command3_Click()
    Dim sts As Integer

    If (StartFlag = 1) Then
        Exit Sub
    End If
    StartFlag = 1
    Text1.Text = "START"
    List1.Clear
    List1.AddItem "NOT MAKE"
    Text1.Text = "END"
    StartFlag = 0
End Sub
---------------------------------------------------------------------------------------------------

Private Sub Command4_Click()
    Dim sts As Integer

    If (StartFlag = 1) Then
        Exit Sub
    End If
    StartFlag = 1
    Text1.Text = "START"
    List1.Clear
    List1.AddItem "NOT MAKE"
    Text1.Text = "END"
    StartFlag = 0
End Sub
---------------------------------------------------------------------------------------------------

Private Sub Command5_Click()
    Dim sts As Integer

    If (StartFlag = 1) Then
        Exit Sub
    End If
    StartFlag = 1
    Text1.Text = "START"
    List1.Clear
    List1.AddItem "NOT MAKE"
    Text1.Text = "END"
    StartFlag = 0
End Sub
---------------------------------------------------------------------------------------------------

Private Sub Command6_Click()
    Dim sts As Integer

    If (StartFlag = 1) Then
        Exit Sub
    End If
    StartFlag = 1
    Text1.Text = "START"
    List1.Clear
    List1.AddItem "NOT MAKE"
    Text1.Text = "END"
    StartFlag = 0
End Sub
---------------------------------------------------------------------------------------------------

Private Sub Form_Load()
    StartFlag = 0                                  'Clear Start Flag
    Dev = -1                                       'Clear device id
    addr = 1                                       'GPIB Address = 1
    Command1.Caption = "Sample1(GPIB)" + Chr(13) + "Get Normal Data"
    Command2.Caption = "Sample2(GPIB)" + Chr(13) + "Get Harmonics Data"
    Text1.Text = ""
End Sub
---------------------------------------------------------------------------------------------------

***************************************************************
Sample Program (Output of Normal Measurement Data)
***************************************************************

Sample1(GPIB) Get Normal Data
---------------------------------------------------------------------------------------------------
Private Function GpibNormal() As Integer
    Dim msg As String                              'Command buffer
    Dim qry As String                              'Query buffer
    Dim sts As Integer
    Dim item As Integer
    Dim comma As Integer
    Dim length As Integer
    Dim cnt As Integer

    term = Chr$(10)                                'terminator
    msg = Space$(100)
    qry = Space$(200)

    List1.AddItem "Now Initializing. Wait a moment."
    Dummy = DoEvents()

    sts = InitGpib                                 'Initialize GPIB
    If (sts <> 0) Then
        GpibNormal = 1
        Exit Function
    End If

    'Initialize the settings
    msg = "*RST" + term                            'Initialize the settings
    sts = ilwrt(Dev, msg, Len(msg))                'Send Command
    If (sts < 0) Then
        GoTo GPIBError
    End If

    'Set the measurment condition
    msg = "SAMPLE:HOLD OFF" + term                 'Hold off
    sts = ilwrt(Dev, msg, Len(msg))
    If (sts < 0) Then
        GoTo GPIBError
    End If
    msg = "MODE RMS" + term                        'Measurment Mode = RMS
    sts = ilwrt(Dev, msg, Len(msg))
    If (sts < 0) Then
        GoTo GPIBError
    End If
    msg = "FILTER OFF" + term                      'Frequency Filter off
    sts = ilwrt(Dev, msg, Len(msg))
    If (sts < 0) Then
        GoTo GPIBError
    End If
    msg = "LFILTER OFF" + term                     'Line Filter off
    sts = ilwrt(Dev, msg, Len(msg))
    If (sts < 0) Then
        GoTo GPIBError
    End If
    msg = "SCALING OFF;AVERAGING OFF" + term       'Scaling & Averaging off
    sts = ilwrt(Dev, msg, Len(msg))
    If (sts < 0) Then
        GoTo GPIBError
    End If

    'Set the measurment range
    msg = "VOLTAGE:RANGE 150V" + term              'Voltage range = 150V
    sts = ilwrt(Dev, msg, Len(msg))
    If (sts < 0) Then
        GoTo GPIBError
    End If
    msg = "CURRENT:RANGE 5A" + term                'Current range = 5A
    sts = ilwrt(Dev, msg, Len(msg))
    If (sts < 0) Then
        GoTo GPIBError
    End If

    'For measure the voltage frequency of element1, set function and element of displayC.
    msg = "DISPLAY3:FUNCTION VHZ;ELEMENT 1" + term
    sts = ilwrt(Dev, msg, Len(msg))
    If (sts < 0) Then
        GoTo GPIBError
    End If

    'Set the communication output items
    '1. V/A/W -> on, others -> off
    msg = "MEASURE:ITEM:PRESET NORMAL" + term
    sts = ilwrt(Dev, msg, Len(msg))
    If (sts < 0) Then
        GoTo GPIBError
    End If
    '2. Element1 VHz -> on
    msg = "MEASURE:ITEM:VHZ:ELEMENT1 ON" + term
    sts = ilwrt(Dev, msg, Len(msg))
    If (sts < 0) Then
        GoTo GPIBError
    End If

    'Set the transition filter used to detect the completion of the data updating
    msg = "STATUS:FILTER1 FALL" + term             'Falling edge of bit0(UPD)
    sts = ilwrt(Dev, msg, Len(msg))
    If (sts < 0) Then
        GoTo GPIBError
    End If

    Sleep 1000
    List1.Clear
    
    'Read and display the measurement data  (It is repeated 10 times in this program)
    For cnt = 1 To 10
        'Clear the extended event register (Read and trash the response)
        msg = "STATUS:EESR?" + term
        sts = ilwrt(Dev, msg, Len(msg))
        sts = ilrd(Dev, qry, Len(qry))
       
       'Wait for the completion of the data updating
        msg = "COMMUNICATE:WAIT 1" + term
        sts = ilwrt(Dev, msg, Len(msg))
        If (sts < 0) Then
            GoTo GPIBError
        End If

        'Read out the measurement data
        msg = "MEASURE:NORMAL:VALUE?" + term
        sts = ilwrt(Dev, msg, Len(msg))
        If (sts < 0) Then
            GoTo GPIBError
        End If
        sts = ilrd(Dev, qry, Len(qry))
        If (sts < 0) Then
            GoTo GPIBError
        End If
       
        'Extract items that are separated by commas(,) from the received data
        List1.AddItem "Measurement - " + CStr(cnt)
        List1.ListIndex = List1.ListIndex + 1
        For item = 1 To 13
            length = Len(qry)
            comma = InStr(qry, ",")
            If (comma = 0) Then comma = InStr(qry, term)
            If (comma = 0) Then Exit For
            Query(item) = Left(qry, comma - 1)
            If item < 10 Then
                List1.AddItem " " + CStr(item) + "  " + Query(item)
            Else
                List1.AddItem CStr(item) + "  " + Query(item)
            End If
            qry = Mid(qry, comma + 1)
            List1.ListIndex = List1.ListIndex + 1
        Next item
        List1.AddItem ""
        List1.ListIndex = List1.ListIndex + 1
        qry = Space$(200)
        Dummy = DoEvents()
    Next cnt

    List1.AddItem "  All end"
    List1.ListIndex = List1.ListIndex + 1
    Call ibonl(Dev, 0)
    GpibNormal = 0
    Exit Function
    
GPIBError:
    Call DisplayGPIBError(sts, msg)
    GpibNormal = 1
End Function
---------------------------------------------------------------------------------------------------

***************************************************************
Sample Program (Output of Harmonic Measurement Data)
***************************************************************

Sample2(GPIB) Get Harmonics Data
---------------------------------------------------------------------------------------------------
Private Function GpibHarmonics() As Integer
    Dim msg As String                              'Command buffer
    Dim qry As String                              'Query buffer
    Dim sts As Integer
    Dim cnt As Integer
    Dim item As Integer
    Dim comma As Integer
    Dim length As Integer

    term = Chr$(10)                                'terminator
    msg = Space$(100)
    qry = Space$(1000)

    List1.AddItem "Now Initializing. Wait a moment."
    Dummy = DoEvents()

    sts = InitGpib                                 'Initialize GPIB
    If (sts <> 0) Then
        GpibHarmonics = 1
        Exit Function
    End If

    'Initialize the settings
    msg = "*RST" + term                            'Initialize the settings
    sts = ilwrt(Dev, msg, Len(msg))                'Send Command
    If (sts < 0) Then
        GoTo GPIBError
    End If

    'Set the measurment condition
    msg = "SAMPLE:RATE 500MS" + term               'Update rate = 500ms
    sts = ilwrt(Dev, msg, Len(msg))
    If (sts < 0) Then
        GoTo GPIBError
    End If
    
    'Set the measurment range
    msg = "VOLTAGE:RANGE 150V" + term              'Voltage range = 150V
    sts = ilwrt(Dev, msg, Len(msg))
    If (sts < 0) Then
        GoTo GPIBError
    End If
    msg = "CURRENT:RANGE 5A" + term                'Current range = 5A
    sts = ilwrt(Dev, msg, Len(msg))
    If (sts < 0) Then
        GoTo GPIBError
    End If

    'Setting related to harmonics analize
    'Object element = 1, PLL source = V1, Computation method of THD = IEC
    msg = "HARMONICS:ELEMENT 1;SYNCHRONIZE V,1;THD IEC;STATE ON" + term
    sts = ilwrt(Dev, msg, Len(msg))                'Send Command
    If (sts < 0) Then
        GoTo GPIBError
    End If

    'Set the communication output items
    '1. All function -> off
    msg = "MEASURE:HARMONICS:ITEM:PRESET CLEAR" + term
    sts = ilwrt(Dev, msg, Len(msg))
    If (sts < 0) Then
        GoTo GPIBError
    End If
    '2. Necessary function -> on
    msg = "MEASURE:HARMONICS:ITEM:SYNCHRONIZE ON;ATHD ON;A ON" + term
    sts = ilwrt(Dev, msg, Len(msg))
    If (sts < 0) Then
        GoTo GPIBError
    End If

    'Set the transition filter used to detect the completion of the data updating
    msg = "STATUS:FILTER1 FALL" + term             'Falling edge of bit0(UPD)
    sts = ilwrt(Dev, msg, Len(msg))
    If (sts < 0) Then
        GoTo GPIBError
    End If

    Sleep 1000
    List1.Clear
    
    'Read and display the harmonics data  (It is repeated 10 times in this program)
    For cnt = 1 To 10
        'Clear the extended event register (Read and trash the response)
        msg = "STATUS:EESR?" + term
        sts = ilwrt(Dev, msg, Len(msg))
        If (sts < 0) Then
            GoTo GPIBError
        End If
        sts = ilrd(Dev, qry, Len(qry))                 'Receive Query
        If (sts < 0) Then
            GoTo GPIBError
        End If
        
        'Wait for the completion of the data updating
        msg = "COMMUNICATE:WAIT 1" + term
        sts = ilwrt(Dev, msg, Len(msg))
        If (sts < 0) Then
            GoTo GPIBError
        End If
        
        'Read out the harmonics data
        msg = "MEASURE:HARMONICS:VALUE?" + term
        sts = ilwrt(Dev, msg, Len(msg))
        If (sts < 0) Then
            GoTo GPIBError
        End If
        sts = ilrd(Dev, qry, Len(qry))
        If (sts < 0) Then
            GoTo GPIBError
        End If
        
        'Extract items that are separated by commas(,) from the received data
        List1.AddItem "Measurement - " + CStr(cnt)
        List1.ListIndex = List1.ListIndex + 1
        For item = 1 To 53
            length = Len(qry)
            comma = InStr(qry, ",")
            If (comma = 0) Then comma = InStr(qry, term)
            Query(item) = Left(qry, comma - 1)
            If (item = 1) Then
                List1.AddItem "FREQ (V1)" + " " + Query(item)
            ElseIf (item = 2) Then
                List1.AddItem "A1 THD   " + " " + Query(item)
            ElseIf (item = 3) Then
                List1.AddItem "A1 Total " + " " + Query(item)
            ElseIf (item = 4) Then
                List1.AddItem "A1 Or.1  " + " " + Query(item)
            ElseIf (item < 13) Then
                List1.AddItem "      " + CStr(item - 3) + "   " + Query(item)
            Else
                List1.AddItem "     " + CStr(item - 3) + "   " + Query(item)
            End If
            qry = Mid(qry, comma + 1)
            List1.ListIndex = List1.ListIndex + 1
        Next item
        List1.AddItem ""
        List1.ListIndex = List1.ListIndex + 1
        qry = Space$(1000)
        Dummy = DoEvents()
    Next cnt

    List1.AddItem "  All end"
    List1.ListIndex = List1.ListIndex + 1
    Call ibonl(Dev, 0)
    GpibHarmonics = 0
    Exit Function

GPIBError:
    Call DisplayGPIBError(sts, msg)
    GpibHarmonics = 1
End Function
---------------------------------------------------------------------------------------------------
