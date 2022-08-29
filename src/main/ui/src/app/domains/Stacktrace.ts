export class Stacktrace {
  cause: string | undefined;
  stackTrace: StacktraceEntry[] = [];
  localizedMessage: string | undefined;
  message: string | undefined;
  suppressed: any [] = [];
}

export class StacktraceEntry {
  methodName: string | undefined;
  fileName: string | undefined;
  lineNumber: string | undefined;
  className: string | undefined;
  nativeMethod: boolean = false;
}
